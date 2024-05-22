var easyMDE = new EasyMDE({
  element: document.getElementById("statement"),
});
 // document
      //   .getElementById("problemForm")
      //   .addEventListener("submit", function (e) {
      //     console.log("form submit");
      //     e.preventDefault();
      //     var formData = new FormData(this);
      //     formData.set("statement", easyMDE.value());

      //     let object = {};
      //     formData.forEach((value, key) => (object[key] = value));
      //     var jsonObject = JSON.stringify(object);

      //     fetch("/api/problem", {
      //       method: "POST",
      //       body: jsonObject,
      //       headers: {
      //         Accept: "application/json",
      //         "Content-Type": "application/json",
      //       },
      //     })
      //       .then((response) => response.json())
      //       .then((data) => {
      //         if (data.success) {
      //           alert("Problem created successfully!");
      //           this.reset();
      //           easyMDE.value("");
      //         } else {
      //           alert("An error occurred. Please try again.");
      //         }
      //       })
      //       .catch((error) => console.error("Error:", error));
      //   });