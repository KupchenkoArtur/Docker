const gerData= async (url)=>{
    const res=await fetch(url);
    const json=await res.json();
}

const url='http://localhost:8080/api/11';
const data=gerData(url);
console.log(data)
