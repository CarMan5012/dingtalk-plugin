Behaviour.specify('.dt-notifier-config-raw', 'dt-notifier-config-raw', 0, function (element) {
    function getContext(el) {
        var parent = el.parentElement;
        while (parent) {
            if (parent.querySelector('.dt-raw-content-builtin') && parent.querySelector('.dt-raw-content-custom')) {
                return parent;
            }
            parent = parent.parentElement;
        }
        return document;
    }

    function toggle() {
        var context = getContext(element);
        var builtin = context.querySelector('.dt-raw-content-builtin');
        var custom = context.querySelector('.dt-raw-content-custom');
        if (element.checked) {
            if (builtin) builtin.style.display = 'none';
            if (custom) custom.style.display = '';
        } else {
            if (builtin) builtin.style.display = '';
            if (custom) custom.style.display = 'none';
        }
    }

    element.onchange = toggle;
    toggle();
})
