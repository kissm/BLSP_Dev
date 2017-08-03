function getLocalTime(nS) {       
      return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");        
}   

function domIsNotNull(domObj) {
    if (domObj === null || domObj === 'null' || domObj === '' || domObj === undefined || domObj === 'undefined') {
        return false;
    }
    return true;
}