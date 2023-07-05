/*
let allUsers = [];
let inputDataSearch = document.querySelector('#inputDataSearch');
let inputBtnDataSearch = document.querySelector('#inputBtnDataSearch');
let titleNoneUser = document.querySelector('#titleNoneUser');
let titleNoneView = document.querySelector('#titleNoneView');
let countUsers = 0;
let tabFilteredUsers = document.querySelector('#tabFilteredUsers');
let tabFilteredStatisticsUsers = document.querySelector('#tabFilteredStatisticsUsers');


window.addEventListener('load', () => {
    fetchApiUsers();
    inputData();
    preventFormSubmit();
    searchData();
})

async function fetchApiUsers() {
    const res = await fetch('https://randomuser.me/api/?seed=javascript&results=100&nat=BR&noinfo');
    const json = await res.json();
    allUsers = json.results.map(user => {
        const { name, picture, dob, gender } = user;

        return {
            name: `${name.first} ${name.last}`,
            picture: picture.thumbnail,
            dob: dob.age,
            gender
        }
    })
}

function preventFormSubmit() {
    let form = document.querySelector('form');

    form.addEventListener('submit', (event) => {
        event.preventDefault();
    })
}

function inputData() {
    inputDataSearch.addEventListener('input', () => {
        inputDataSearch.value.toLowerCase();
        let qtdDataSearch = inputDataSearch.value.length;

        if (qtdDataSearch !== 0) {
            inputBtnDataSearch.removeAttribute('disabled')
        } else {
            inputBtnDataSearch.setAttribute('disabled', 'disabled')
        }
    })

    clearInput();
}

function clearInput() {
    inputDataSearch.value = '';
    inputDataSearch.focus();
}

function searchData() {
    inputBtnDataSearch.addEventListener('click', () => {
        render();
        clearInput();
    })
}

function render() {
    renderFoundUsers();
    renderStatisticsUsers();
}

function renderFoundUsers() {
    const searchUser = allUsers.filter(user => {
        let valueDataSearch = inputDataSearch.value;
        let userNameLowercase = user.name.toLowerCase();

        return userNameLowercase.indexOf(valueDataSearch) >= 0
    });

    let usersHTML = '<div>';

    searchUser.forEach(user => {
        const { name, picture, dob} = user;

        titleNoneUser.innerHTML = `
                <h2 class="mb-3">
                    <span>${searchUser.length}</span>
                    usuário(s) encontrado(s) para o resultado: ${inputDataSearch.value}
                </h2>
            `;

        const userHTML = `
                <p class="d-flex align-items-center text-primary">
                    <img src="${picture}" title="${name}" alt="${name}" class="rounded-circle pr-2">
                    <span>${name}, </pan>
                    <span>${dob} anos</pan>                
                </p>
            `;
        usersHTML += userHTML;

    })

    usersHTML += '</div>';
    tabFilteredUsers.innerHTML = usersHTML;
}

function renderStatisticsUsers() {
    const searchUser = allUsers.filter(user => {
        let valueDataSearch = inputDataSearch.value;
        let userNameLowercase = user.name.toLowerCase();

        return userNameLowercase.indexOf(valueDataSearch) >= 0
    });

    const genderMasc = searchUser.filter(user => {
        return user.gender === "male";
    });

    const genderFem = searchUser.filter(user => {
        return user.gender === "female";
    });

    const userIdades = searchUser.reduce((accumulate, current) => {
        return accumulate + current.dob;
    }, 0);

    const mediaIdades = userIdades / searchUser.length;

    let statisticsHTML = '<div>';

    titleNoneView.innerHTML = `
        <h2 class="mb-3">
            Estatísticas
        </h2>
    `;

    const statisticHTML = `
        <p class="text-primary">Sexo masculino: <span>${genderMasc.length}</span></p>
        <p class="text-primary">Sexo feminino: <span>${genderFem.length}</span></p>
        <p class="text-primary">Soma das idades: <span>${userIdades}</span></p>
        <p class="text-primary">Média das idades: <span>${mediaIdades}</span></p>
    `;

    statisticsHTML += statisticHTML;
    statisticsHTML += '</div>';
    tabFilteredStatisticsUsers.innerHTML = statisticsHTML;
}
*/
let table = {
    id: 0,
    title: "root - not displayed",
    children: [{
        id: 1,
        title: "João Paulo",
        children: [{
            id: 11,
            title: "Rafael",
            children: [{
                id: 111,
                title: "Paulo"
            }, {
                id: 112,
                title: "José"
            }]
        }, {
            id: 12,
            title: "Rodrigo"
        }]
    }, {
        id: 2,
        title: "Rodrigo",
        children: [{
            id: 21,
            title: "Felipe"
        }, {
            id: 22,
            title: "Marcos"
        }]
    }, {
        id: 3,
        title: "Antonio",
        children: [{
            id: 4,
            title: "Sergio"
        }, {
            id: 5,
            title: "Jeferson"
        }]
    }]
};

let tableStoraged = localStorage.getItem('table')
let data = JSON.parse(tableStoraged);

let checksStoraged = localStorage.getItem('check')
let checks = JSON.parse(checksStoraged);

var checkeds = [];

if(!tableStoraged) localStorage.setItem('table', JSON.stringify(table));
if(checksStoraged){
    checkeds = checks
}

function addItem(parentUL, branch) {
    for (let key in branch.children) {
        let item = branch.children[key];
        let id = `item${item.id}`

        if(checksStoraged){
            let location = checkeds.findIndex(item => item.id === id)
            $item = $('<li>', {
                id: "item" + item.id
            });
            $item.append($('<input>', {
                type: "checkbox",
                id: "item" + item.id,
                name: "item" + item.id,
                "checked": checkeds[location].checked
            }));
            $item.append($('<label>', {
                for: "item" + item.id,
                text: item.title
            }));
            parentUL.append($item);
            if (item.children) {
                let location = checkeds.findIndex(item => item.id === id)
                if(checkeds[location].checked){
                    var $ul = $('<ul>').appendTo($item);
                    addItem($ul, item);
                }else{
                    var $ul = $('<ul>', {
                        style: 'display: none'
                    }).appendTo($item);
                    addItem($ul, item);
                }
            }

        }else{
            checkeds.push({
                id: id,
                checked: false
            })
            $item = $('<li>', {
                id: "item" + item.id
            });
            $item.append($('<input>', {
                type: "checkbox",
                id: "item" + item.id,
                name: "item" + item.id
            }));
            $item.append($('<label>', {
                for: "item" + item.id,
                text: item.title
            }));
            parentUL.append($item);
            if (item.children) {
                var $ul = $('<ul>', {
                    style: 'display: none'
                }).appendTo($item);
                addItem($ul, item);
            }
        }
    }
}

$(function () {


    $('#form').submit(function(){
        var dados = new FormData(this);
     
        $.ajax({
            url: '/',
            type: "POST",
            data: dados,
            processData: false,
            cache: false,
            contentType: false,
            success: function( data ) {
                console.log(data);
            },
            error: function (request, status, error) {
                alert(request.responseText);
            }
        });
        return false;
      });

    addItem($('#root'), data);
    $(':checkbox').change(function () {
        $(this).closest('li').children('ul').slideToggle();

        let location = checkeds.findIndex(item => item.id === $(this).closest('li')[0].firstChild.name)

        if(location >= 0){
            checkeds[location].checked = $(':checkbox')[location].checked;
        }

        localStorage.setItem('check', JSON.stringify(checkeds))
    });
    $('label').click(function(){
        $(this).closest('li').find(':checkbox').trigger('click');
    });

});