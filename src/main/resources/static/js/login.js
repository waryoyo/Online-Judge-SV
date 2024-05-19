function login() {
  const result = fetch("/api/auth/login", {
    method: "POST",
    credentials: 'same-origin',
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ usernameOrEmail: "mario",  password:"123" }),
  })
    .then((response) => response.text())
    .then((text) => console.log(text))
    .then(() => fetch("/api/auth/user", {
      credentials: 'same-origin'
    }))
    .then(response => response.text())
    .then(textResponse => console.log(textResponse))

}