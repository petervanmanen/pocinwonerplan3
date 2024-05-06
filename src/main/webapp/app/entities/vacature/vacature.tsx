import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vacature.reducer';

export const Vacature = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vacatureList = useAppSelector(state => state.vacature.entities);
  const loading = useAppSelector(state => state.vacature.loading);

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
      <h2 id="vacature-heading" data-cy="VacatureHeading">
        Vacatures
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vacature/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vacature
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vacatureList && vacatureList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumgesloten')}>
                  Datumgesloten <FontAwesomeIcon icon={getSortIconByFieldName('datumgesloten')} />
                </th>
                <th className="hand" onClick={sort('datumopengesteld')}>
                  Datumopengesteld <FontAwesomeIcon icon={getSortIconByFieldName('datumopengesteld')} />
                </th>
                <th className="hand" onClick={sort('deeltijd')}>
                  Deeltijd <FontAwesomeIcon icon={getSortIconByFieldName('deeltijd')} />
                </th>
                <th className="hand" onClick={sort('extern')}>
                  Extern <FontAwesomeIcon icon={getSortIconByFieldName('extern')} />
                </th>
                <th className="hand" onClick={sort('intern')}>
                  Intern <FontAwesomeIcon icon={getSortIconByFieldName('intern')} />
                </th>
                <th className="hand" onClick={sort('vastedienst')}>
                  Vastedienst <FontAwesomeIcon icon={getSortIconByFieldName('vastedienst')} />
                </th>
                <th>
                  Vacaturebijfunctie Functie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vacatureList.map((vacature, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vacature/${vacature.id}`} color="link" size="sm">
                      {vacature.id}
                    </Button>
                  </td>
                  <td>
                    {vacature.datumgesloten ? (
                      <TextFormat type="date" value={vacature.datumgesloten} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vacature.datumopengesteld}</td>
                  <td>{vacature.deeltijd ? 'true' : 'false'}</td>
                  <td>{vacature.extern ? 'true' : 'false'}</td>
                  <td>{vacature.intern ? 'true' : 'false'}</td>
                  <td>{vacature.vastedienst ? 'true' : 'false'}</td>
                  <td>
                    {vacature.vacaturebijfunctieFunctie ? (
                      <Link to={`/functie/${vacature.vacaturebijfunctieFunctie.id}`}>{vacature.vacaturebijfunctieFunctie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vacature/${vacature.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vacature/${vacature.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vacature/${vacature.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vacatures found</div>
        )}
      </div>
    </div>
  );
};

export default Vacature;
