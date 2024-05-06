import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './declaratie.reducer';

export const Declaratie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const declaratieList = useAppSelector(state => state.declaratie.entities);
  const loading = useAppSelector(state => state.declaratie.loading);

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
      <h2 id="declaratie-heading" data-cy="DeclaratieHeading">
        Declaraties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/declaratie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Declaratie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {declaratieList && declaratieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumdeclaratie')}>
                  Datumdeclaratie <FontAwesomeIcon icon={getSortIconByFieldName('datumdeclaratie')} />
                </th>
                <th className="hand" onClick={sort('declaratiebedrag')}>
                  Declaratiebedrag <FontAwesomeIcon icon={getSortIconByFieldName('declaratiebedrag')} />
                </th>
                <th className="hand" onClick={sort('declaratiestatus')}>
                  Declaratiestatus <FontAwesomeIcon icon={getSortIconByFieldName('declaratiestatus')} />
                </th>
                <th>
                  Ingedienddoor Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Soortdeclaratie Declaratiesoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Dientin Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {declaratieList.map((declaratie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/declaratie/${declaratie.id}`} color="link" size="sm">
                      {declaratie.id}
                    </Button>
                  </td>
                  <td>{declaratie.datumdeclaratie}</td>
                  <td>{declaratie.declaratiebedrag}</td>
                  <td>{declaratie.declaratiestatus}</td>
                  <td>
                    {declaratie.ingedienddoorLeverancier ? (
                      <Link to={`/leverancier/${declaratie.ingedienddoorLeverancier.id}`}>{declaratie.ingedienddoorLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {declaratie.soortdeclaratieDeclaratiesoort ? (
                      <Link to={`/declaratiesoort/${declaratie.soortdeclaratieDeclaratiesoort.id}`}>
                        {declaratie.soortdeclaratieDeclaratiesoort.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {declaratie.dientinWerknemer ? (
                      <Link to={`/werknemer/${declaratie.dientinWerknemer.id}`}>{declaratie.dientinWerknemer.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/declaratie/${declaratie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/declaratie/${declaratie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/declaratie/${declaratie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Declaraties found</div>
        )}
      </div>
    </div>
  );
};

export default Declaratie;
