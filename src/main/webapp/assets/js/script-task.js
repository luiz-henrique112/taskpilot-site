document.addEventListener("DOMContentLoaded", function () {
   let btnREM = document.getElementById("btn-remove-form");
   let btnREM_ETD = document.getElementById("btn-remove-form-ETD");
   let btnREM_PV = document.getElementById("btn-remove-PV")
   let btn_plus = document.getElementById("btn-plus");
   let btn_settings = document.getElementById("settings");
   let form_background = document.getElementById("form-background");
   let form_ETD_background = document.getElementById("ETD_background");
   let settings_background = document.getElementById("settings-background")
   let _PV_background = document.getElementById("password-verifyingBackground");
   let btnREM_settings = document.getElementById("btn-remove-settings")
   let taskID_inputValue = document.getElementById("taskID")
   let menu_icon = document.querySelectorAll(".menu-icon")
   let submit_button = document.getElementById("submit-button");
   let submit_button_ETD = document.getElementById("submit-button_ETD");
   let submit_button_DT = document.getElementById("submit-button_DT");
   let submit_button_PV =document.getElementById("submit-button-PV")
   let term_input = document.getElementById("term-input")
   let input_name = document.getElementById("input_name");
   let input_desc = document.getElementById("input_desc");
   let input_term = document.getElementById("input_term");
   let actionInput = document.getElementById("actionInput")
   
   btn_plus.addEventListener('click', () => {
      form_background.classList.remove("invisible");
   });

   btn_settings.addEventListener('click', () => {
      _PV_background.classList.remove("invisible");
   });

   btnREM.addEventListener('click', () => {
      form_background.classList.add("invisible");
   });

   btnREM_ETD.addEventListener('click', () => {
      form_ETD_background.classList.add("invisible");
   });

   btnREM_settings.addEventListener('click', () => {
      settings_background.classList.add("invisible");
   });

   btnREM_PV.addEventListener('click', () => {
      _PV_background.classList.add("invisible");
   })

   btn_plus.addEventListener('click', () => {
      form_background.classList.remove("invisible");
   });

   submit_button.addEventListener('click', () => {
      setTimeout(()=>{
         form_background.classList.add("invisible");
      },100)
   });
   submit_button_PV.addEventListener('click', () => {
      setTimeout(()=>{
         form_background.classList.add("invisible");
      },100)
   });
   
   submit_button_ETD.addEventListener('click', () => {
      setTimeout(()=>{
         form_ETD_background.classList.add("invisible");
      },100)
   });
   
   submit_button_DT.addEventListener('click', () => {
         actionInput.value = "delete-task"
   });
   
   let today = new Date().toISOString().split("T")[0];
   term_input.value = today;


   menu_icon.forEach((btn) => {
      btn.addEventListener('click', () => {
         let task_div_container = btn.parentNode
         let TDC_ID = task_div_container.id
         taskID = TDC_ID;
         let name_txt;
         let desc_txt;
         let term_txt;
         

         taskData_texts = task_div_container.children
         
         name_txt = taskData_texts[1].textContent
         desc_txt = taskData_texts[2].textContent
         term_txt = taskData_texts[3].textContent
         
         taskID_inputValue.value =  "" + TDC_ID + "";
         form_ETD_background.classList.remove("invisible");

         input_name.value = name_txt;
         input_desc.value = desc_txt;
         input_term.value = term_txt;

         console.log(term_txt)
         console.log(name_txt)
         console.log(description_txt)
      })
   })
});
//             
