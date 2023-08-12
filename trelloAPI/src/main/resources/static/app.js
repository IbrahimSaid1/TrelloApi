const host = window.location.hostname
const BASE_URL = 'http://'+host+':8080/api';
var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };
        fetch("http://"+host+":8080/boards/1/cards", requestOptions)
            .then(response => response.json()) // Parse response as JSON
            .then(result => {
                // Process each element using forEach
                result.forEach(element => {
                    let sectionNumber = element.section;
                    console.log(sectionNumber);
                    let columnDiv;
                    if (sectionNumber === 1) {
                        columnDiv = document.getElementById("todo");
                    }
                    else if (sectionNumber === 2) {
                        columnDiv = document.getElementById("inprogress");
                    }
                    else {
                        columnDiv = document.getElementById("done");
                    }
                    let cardDiv = document.createElement("div");
                    cardDiv.className = "card";
                    let idDiv = document.createElement("idDiv");
                    idDiv.className = "idDiv";
                    idDiv.textContent = element.cardId;
                    let titleDiv = document.createElement("titleDiv");
                    titleDiv.className = "titleDiv";
                    titleDiv.textContent = element.title;
                    let descriptionDiv = document.createElement("descriptionDiv");
                    descriptionDiv.className = "descriptionDiv";
                    descriptionDiv.textContent = element.description;
                    cardDiv.appendChild(idDiv);
                    cardDiv.appendChild(titleDiv);
                    cardDiv.appendChild(descriptionDiv);
                    columnDiv.appendChild(cardDiv);
                    console.log(element); // For example, log the element to the console
                });
            })
            .catch(error => console.log('error', error)); // Handle errors
            var requestOptions = {
  method: 'PUT',
  redirect: 'follow'
};
// Function to create a new board
async function createBoard() {
    const newBoardNameElement = document.getElementById('new-board-name');
    const newBoardName = newBoardNameElement.value.trim();
    if (!newBoardName) {
      alert('Please enter a valid board name.');
      return;
    }
    try {
      const response = await fetch(`${BASE_URL}/board`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: newBoardName,
          columns: 'To Do,In Progress,Done',
        }),
      });
      const data = await response.json();
      console.log('New board created:', data);
      newBoardNameElement.value = '';
      // Add the new board to the board list
      const boardList = document.getElementById('board-list');
      const boardItem = document.createElement('li');
      boardItem.textContent = data.name;
      boardList.appendChild(boardItem);
    } catch (error) {
      console.error('Error creating board:', error);
    }
  }
    // Function to create a new card in a board
async function createCard() {
  const boardSelectElement = document.getElementById('board-select');
  const selectedBoardId = boardSelectElement.value;
  const newCardTitleElement = document.getElementById('new-card-title');
  const newCardTitle = newCardTitleElement.value.trim();
  const newCardDescriptionElement = document.getElementById('new-card-description');
  const newCardDescription = newCardDescriptionElement.value.trim();
  const columnSelectElement = document.getElementById('column-select');
  const selectedColumn = columnSelectElement.value;
  if (!newCardTitle || !selectedBoardId) {
      alert('Please enter a valid card title and select a board.');
      return;
  }
  try {
      const response = await fetch(`http://`+host+`:8080/boards/${selectedBoardId}/cards`, {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          body: JSON.stringify({
              title: newCardTitle,
              description: newCardDescription,
              section: parseInt(selectedColumn),
          }),
      });
      if (response.ok) {
          console.log('New card created successfully');
          // Clear input fields after successful create
          newCardTitleElement.value = '';
          newCardDescriptionElement.value = '';
          // Reload the page after successful create
          location.reload();
      } else {
          console.error('Failed to create card');
      }
  } catch (error) {
      console.error('Error creating card:', error);
  }
}
// Function to fetch existing boards and populate the select options
async function fetchBoardsAndPopulateSelect() {
  try {
      const response = await fetch(`${BASE_URL}/board`);
      const data = await response.json();
      const boardSelectElement = document.getElementById('board-select');
      data.forEach((board) => {
          const option = document.createElement('option');
          option.value = board.boardId;
          option.textContent = board.name;
          boardSelectElement.appendChild(option);
      });
      // After populating the select options, load cards for each board
      loadCards();
  } catch (error) {
      console.error('Error fetching boards:', error);
  }
}
// Fetch existing boards on page load
fetchBoardsAndPopulateSelect();
// Function to fetch and display existing cards
function loadCards() {
  const boardSelectElement = document.getElementById('board-select');
  const selectedBoardId = boardSelectElement.value;
  const storedCards = JSON.parse(localStorage.getItem(`cards-${selectedBoardId}`)) || [];
  storedCards.forEach(card => {
      displayCard(card, selectedBoardId);
  });
}
loadCards();
// Function to update a card
async function updateCard() {
  const cardId = document.getElementById('updateCardIdInput').value;
  const newTitle = document.getElementById('updateCardNameInput').value;
  const newDescription = document.getElementById('updateCardDescriptionInput').value;
  const newSection = document.getElementById('updateColumnSelect').value;
  if (!cardId || !newTitle) {
      alert('Please enter a valid card ID and title.');
      return;
  }
  const updateData = {
      title: newTitle,
      description: newDescription,
      section: parseInt(newSection),
  };
  const requestOptions = {
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(updateData),
      redirect: 'follow',
  };
  try {
      const response = await fetch(`http://`+host+`:8080/boards/1/cards/${cardId}`, requestOptions);
      if (response.ok) {
          console.log('Card updated successfully');
          // Reload the page after successful update
          location.reload();
      } else {
          console.error('Failed to update card');
      }
  } catch (error) {
      console.error('Error updating card:', error);
  }
}
// Attach event listener to the Delete button
const deleteButton = document.getElementById('deleteButton');
deleteButton.addEventListener('click', deleteCard);
// Function to delete a card
function deleteCard() {
  let cardDeleteId = document.getElementById("deleteTaskId").value;
  var requestOptions = {
      method: 'DELETE',
      redirect: 'follow'
  };
  fetch(`http://`+host+`:8080/boards/1/cards/${cardDeleteId}`, requestOptions)
  .then(response => {
      if (response.ok) {
          console.log('Card deleted successfully');
          // Reload the page after successful delete
          location.reload();
      } else {
          console.error('Failed to delete card');
      }
  })
  .catch(error => console.log('error', error));
}