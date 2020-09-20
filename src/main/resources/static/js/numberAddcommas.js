function addcommas(sValue) {
    var sRegExp = new RegExp('(-?[0-9]+)([0-9]{3})');
    while(sRegExp.test(sValue)) {
        sValue = sValue.replace(sRegExp, '$1,$2');
    }
    return sValue;
}