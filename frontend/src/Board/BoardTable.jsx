import {
    TableContainer,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableCell,
    Paper,
} from "@mui/material";

function makeBoardRow(data, i) {
    return (
        <TableRow key={i}>
            <TableCell>{data.id}</TableCell>
            <TableCell>{data.title}</TableCell>
            <TableCell>{data.author}</TableCell>
            <TableCell>{data.timestamp}</TableCell>
            <TableCell>{data.views}</TableCell>
        </TableRow>
    );
}

function BoardTable(props) {
    const boardList = props.boardList;

    return (
        <TableContainer component={Paper}>
            <Table aria-label="simple table" sx={{ size: "small" }}>
                <TableHead>
                    <TableRow>
                        <TableCell>id</TableCell>
                        <TableCell>title</TableCell>
                        <TableCell>author</TableCell>
                        <TableCell>date</TableCell>
                        <TableCell>views</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {boardList.map((x, i) => {
                        return makeBoardRow(x, i);
                    })}
                </TableBody>
            </Table>
        </TableContainer>
    );
}


/***
 * Data For Test Purpose
 */
//const boardData = [
//    {
//        id: 1,
//        title: "Desktop Support Technician",
//        author: "Rodgier",
//        timestamp: "4/6/2024",
//        views: 47,
//    },
//    {
//        id: 2,
//        title: "Physical Therapy Assistant",
//        author: "Gilbart",
//        timestamp: "10/24/2023",
//        views: 31,
//    },
//    {
//        id: 3,
//        title: "Executive Secretary",
//        author: "Carrane",
//        timestamp: "7/17/2023",
//        views: 62,
//    },
//    {
//        id: 4,
//        title: "Actuary",
//        author: "Crews",
//        timestamp: "4/21/2023",
//        views: 88,
//    },
//    {
//        id: 5,
//        title: "Analyst Programmer",
//        author: "Strahan",
//        timestamp: "7/29/2023",
//        views: 72,
//    },
//    {
//        id: 6,
//        title: "Actuary",
//        author: "Haestier",
//        timestamp: "10/12/2023",
//        views: 32,
//    },
//    {
//        id: 7,
//        title: "Actuary",
//        author: "Bowlas",
//        timestamp: "8/16/2023",
//        views: 45,
//    },
//    {
//        id: 8,
//        title: "Paralegal",
//        author: "Bullocke",
//        timestamp: "7/6/2023",
//        views: 38,
//    },
//    {
//        id: 9,
//        title: "Office Assistant IV",
//        author: "Conway",
//        timestamp: "12/17/2023",
//        views: 63,
//    },
//    {
//        id: 10,
//        title: "Analyst Programmer",
//        author: "Basson",
//        timestamp: "7/2/2023",
//        views: 87,
//    },
//];

export default BoardTable;
