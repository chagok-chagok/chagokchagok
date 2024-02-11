<script setup>
function show(id) {
  var e = $(id);
  if (e != null) {
    e.style.display = "block";
  }
}

function hide(id) {
  var e = $(id);
  if (e != null) {
    e.style.display = "none";
  }
}

function confirmSubmit() {
  var id = $("id");
  var pw = $("pw");
  var encpw = $("encpw");

  if (id.value == "") {
    show("err_empty_id");
    hide("err_empty_pw");
    hide("err_common");
    id.focus();
    return false;
  } else if (pw.value == "") {
    hide("err_empty_id");
    show("err_empty_pw");
    hide("err_common");
    pw.focus();
    return false;
  }
  try {
    $("ls").value = localStorage.getItem("nid_t");
  } catch (e) {}

  return encryptIdPw();
}

function clearErrorLayers() {
  var errors = new Array(
    "err_empty_captcha",
    "err_autologin",
    "err_network_delay",
    "err_unsupport_browser",
    "err_empty_id",
    "err_empty_pw",
    "err_idpw_incorrect",
    "err_grpidpw_incorrect"
  );

  for (var error in errors) {
    e = $(error);
    if (e != null) {
      e.style.display = "none";
    }
  }
}

var inSubmitProgress = false;
function confirmSplitSubmit() {
  if (inSubmitProgress) {
    return false;
  }
  inSubmitProgress = true;
  var id = $("id");
  var pw = $("pw");
  var encpw = $("encpw");

  if (id.value == "") {
    show("err_empty_id");
    hide("err_empty_pw");
    hide("err_common_pw1");
    hide("err_common");
    id.focus();
    inSubmitProgress = false;
    return false;
  } else if (pw.value == "") {
    show("err_empty_pw");
    hide("err_empty_id");
    hide("err_common_pw1");
    hide("err_common");
    pw.focus();
    inSubmitProgress = false;
    return false;
  }
  try {
    $("ls").value = localStorage.getItem("nid_t");
  } catch (e) {}
  return encryptIdPwSplit();
}

function addNormalEventWithType(nclickId, func, eventType) {
  if (isObjExist(nclickId)) {
    var targetElement = $(nclickId);
    if (targetElement.addEventListener) {
      targetElement.addEventListener(eventType, func, false);
    } else {
      targetElement.attachEvent("on" + eventType, func);
    }
  }
}

var focusPw = false;
if (isObjExist("id")) {
  var idElement = $("id");
  var id_line = $("id_line");
  addNormalEventWithType(
    "id",
    function () {
      if (idElement.value.length != 0) {
        show("id_clear");
      }
    },
    "keyup"
  );
  addNormalEventWithType(
    "id",
    function () {
      if (idElement.value.length != 0) {
        show("id_clear");
      }
      id_line.className = "input_row focus on";
    },
    "focus"
  );
  addNormalEventWithType(
    "id",
    function () {
      if (idElement.value.length == 0) {
        setTimeout(function () {
          hide("id_clear");
        }, 200);
      }
      id_line.className = "input_row";
    },
    "blur"
  );
  addNormalEvent("id_clear", function () {
    idElement.value = "";
    hide("id_clear");
  });
  try {
    if (idElement.value.length == 0) {
      idElement.focus();
    } else {
      focusPw = true;
    }
  } catch (e) {}
}
if (isObjExist("pw")) {
  var pwElement = $("pw");
  var pw_line = $("pw_line");
  addNormalEventWithType(
    "pw",
    function () {
      capslockevt(event);
      getKeysv2();
    },
    "keypress"
  );
  addNormalEventWithType(
    "pw",
    function () {
      if (pwElement.value.length != 0) {
        show("pw_clear");
      }
      checkShiftUp(event);
    },
    "keyup"
  );
  addNormalEventWithType(
    "pw",
    function () {
      if (pwElement.value.length != 0) {
        show("pw_clear");
      }
      pw_line.className = "input_row focus on";
    },
    "focus"
  );
  addNormalEventWithType(
    "pw",
    function () {
      if (pwElement.value.length == 0) {
        setTimeout(function () {
          hide("pw_clear");
        }, 200);
      }
      pw_line.className = "input_row";
    },
    "blur"
  );
  addNormalEvent("pw_clear", function () {
    pwElement.value = "";
    hide("pw_clear");
  });
  if (focusPw) {
    pwElement.focus();
  }

  if (isObjExist("pw")) {
    var pwElement = $("pw");
    var pw_line = $("pw_line");
    addNormalEventWithType(
      "pw",
      function () {
        capslockevt(event);
        getKeysv2();
      },
      "keypress"
    );
    addNormalEventWithType(
      "pw",
      function () {
        if (pwElement.value.length != 0) {
          show("pw_clear");
        }
        checkShiftUp(event);
      },
      "keyup"
    );
    addNormalEventWithType(
      "pw",
      function () {
        checkShiftDown(event);
      },
      "keydown"
    );
    addNormalEventWithType(
      "pw",
      function () {
        if (pwElement.value.length != 0) {
          show("pw_clear");
        }
        pw_line.className = "input_row focus on";
      },
      "focus"
    );
    addNormalEventWithType(
      "pw",
      function () {
        if (pwElement.value.length == 0) {
          setTimeout(function () {
            hide("pw_clear");
          }, 200);
        }
        pw_line.className = "input_row";
      },
      "blur"
    );
    addNormalEvent("pw_clear", function () {
      pwElement.value = "";
      hide("pw_clear");
    });
    if (focusPw) {
      pwElement.focus();
    }
  }
}
</script>

<template>
  <div></div>
</template>

<style scoped></style>
