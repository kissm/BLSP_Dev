package com.framework.core.handler;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author wangchaochao
 *
 */
public abstract class MQSynHandler extends AbstractSynHandler {

	public boolean send(Object body) {
		JmsTemplate jmsTemplate = getJmsTemplate();
		try {
			MessageCreator messageCreator = createTxtMsg((String) body);
			jmsTemplate.send(messageCreator);
		} catch (Exception e) {
		}
		return true;
	}

	public abstract JmsTemplate getJmsTemplate();

	private MessageCreator createTxtMsg(final String msg) {
		return new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage();
				textMessage.setText(msg);
				return textMessage;
			}
		};
	}

}
