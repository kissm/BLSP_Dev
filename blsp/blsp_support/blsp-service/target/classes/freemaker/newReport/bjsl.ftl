{<#rt>
'sblsh':'${serviceReport.sblsh!""}',<#rt>
'sxbm':'${serviceReport.sxbm!""}',<#rt>
'bjslblrxm':'${serviceReport.bjslblrxm!""}',<#rt>
<#if serviceReport.bjclqd??>  <#rt>
'bjclqd':'${serviceReport.bjclqd}',<#rt>
<#else><#rt>
'bjclqd':'资料不齐，需补齐资料！',<#rt>
</#if><#rt>
<#if serviceReport.bjsj??><#rt>
'bjsj':'${serviceReport.bjsj}',<#rt>
<#else>  <#rt>
'bjsj':'',<#rt>
</#if> <#rt>
'xzqhdm':'440404',<#rt>
'bjsljtdd':'金湾区',<#rt>
'transactor':'${serviceReport.transactorname!""}',<#rt>
'bz':'',<#rt>
'byzd':''<#rt>
}<#rt>