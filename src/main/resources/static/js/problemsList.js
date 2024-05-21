document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/problem/list")
        .then(response => response.json())
        .then(problems => {
            const problemsTableBody = document.querySelector("#problems-list tbody");
            problems.forEach(problem => {
                const problemRow = document.createElement("tr");
                problemRow.classList.add("table-row");
                problemRow.setAttribute("data-id", problem.id);
                problemRow.onclick = function() {
                    window.location.href = `/problem/${problem.id}`;
                };
                problemRow.innerHTML = `
                    <td>${problem.title}</td>
                    <td class="${problem.difficulty.toLowerCase()}">${problem.difficulty}</td>
                `;
                problemsTableBody.appendChild(problemRow);
            });
        })
        .catch(error => console.error("Error fetching problems:", error))
         .then(() => fetch("/api/auth/user", {
              credentials: 'same-origin'
            }))
            .then(response => response.text())
            .then(textResponse => console.log(textResponse))

});