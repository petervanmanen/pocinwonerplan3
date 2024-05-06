import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './kostenplaats.reducer';

export const Kostenplaats = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kostenplaatsList = useAppSelector(state => state.kostenplaats.entities);
  const loading = useAppSelector(state => state.kostenplaats.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="kostenplaats-heading" data-cy="KostenplaatsHeading">
        Kostenplaats
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/kostenplaats/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kostenplaats
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kostenplaatsList && kostenplaatsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('btwcode')}>
                  Btwcode <FontAwesomeIcon icon={getSortIconByFieldName('btwcode')} />
                </th>
                <th className="hand" onClick={sort('btwomschrijving')}>
                  Btwomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('btwomschrijving')} />
                </th>
                <th className="hand" onClick={sort('kostenplaatssoortcode')}>
                  Kostenplaatssoortcode <FontAwesomeIcon icon={getSortIconByFieldName('kostenplaatssoortcode')} />
                </th>
                <th className="hand" onClick={sort('kostenplaatssoortomschrijving')}>
                  Kostenplaatssoortomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('kostenplaatssoortomschrijving')} />
                </th>
                <th className="hand" onClick={sort('kostenplaatstypecode')}>
                  Kostenplaatstypecode <FontAwesomeIcon icon={getSortIconByFieldName('kostenplaatstypecode')} />
                </th>
                <th className="hand" onClick={sort('kostenplaatstypeomschrijving')}>
                  Kostenplaatstypeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('kostenplaatstypeomschrijving')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th>
                  Heeft Inkooporder <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Taakveld <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Hoofdrekening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Project <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kostenplaatsList.map((kostenplaats, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kostenplaats/${kostenplaats.id}`} color="link" size="sm">
                      {kostenplaats.id}
                    </Button>
                  </td>
                  <td>{kostenplaats.btwcode}</td>
                  <td>{kostenplaats.btwomschrijving}</td>
                  <td>{kostenplaats.kostenplaatssoortcode}</td>
                  <td>{kostenplaats.kostenplaatssoortomschrijving}</td>
                  <td>{kostenplaats.kostenplaatstypecode}</td>
                  <td>{kostenplaats.kostenplaatstypeomschrijving}</td>
                  <td>{kostenplaats.naam}</td>
                  <td>{kostenplaats.omschrijving}</td>
                  <td>
                    {kostenplaats.heeftInkooporders
                      ? kostenplaats.heeftInkooporders.map((val, j) => (
                          <span key={j}>
                            <Link to={`/inkooporder/${val.id}`}>{val.id}</Link>
                            {j === kostenplaats.heeftInkooporders.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {kostenplaats.heeftTaakvelds
                      ? kostenplaats.heeftTaakvelds.map((val, j) => (
                          <span key={j}>
                            <Link to={`/taakveld/${val.id}`}>{val.id}</Link>
                            {j === kostenplaats.heeftTaakvelds.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {kostenplaats.heeftHoofdrekenings
                      ? kostenplaats.heeftHoofdrekenings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/hoofdrekening/${val.id}`}>{val.id}</Link>
                            {j === kostenplaats.heeftHoofdrekenings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {kostenplaats.heeftProjects
                      ? kostenplaats.heeftProjects.map((val, j) => (
                          <span key={j}>
                            <Link to={`/project/${val.id}`}>{val.id}</Link>
                            {j === kostenplaats.heeftProjects.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/kostenplaats/${kostenplaats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/kostenplaats/${kostenplaats.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kostenplaats/${kostenplaats.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Kostenplaats found</div>
        )}
      </div>
    </div>
  );
};

export default Kostenplaats;
