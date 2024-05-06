import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './aanbestedinginhuur.reducer';

export const Aanbestedinginhuur = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aanbestedinginhuurList = useAppSelector(state => state.aanbestedinginhuur.entities);
  const loading = useAppSelector(state => state.aanbestedinginhuur.loading);

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
      <h2 id="aanbestedinginhuur-heading" data-cy="AanbestedinginhuurHeading">
        Aanbestedinginhuurs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/aanbestedinginhuur/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aanbestedinginhuur
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aanbestedinginhuurList && aanbestedinginhuurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanvraaggesloten')}>
                  Aanvraaggesloten <FontAwesomeIcon icon={getSortIconByFieldName('aanvraaggesloten')} />
                </th>
                <th className="hand" onClick={sort('aanvraagnummer')}>
                  Aanvraagnummer <FontAwesomeIcon icon={getSortIconByFieldName('aanvraagnummer')} />
                </th>
                <th className="hand" onClick={sort('datumcreatie')}>
                  Datumcreatie <FontAwesomeIcon icon={getSortIconByFieldName('datumcreatie')} />
                </th>
                <th className="hand" onClick={sort('datumopeningkluis')}>
                  Datumopeningkluis <FontAwesomeIcon icon={getSortIconByFieldName('datumopeningkluis')} />
                </th>
                <th className="hand" onClick={sort('datumsluiting')}>
                  Datumsluiting <FontAwesomeIcon icon={getSortIconByFieldName('datumsluiting')} />
                </th>
                <th className="hand" onClick={sort('datumverzending')}>
                  Datumverzending <FontAwesomeIcon icon={getSortIconByFieldName('datumverzending')} />
                </th>
                <th className="hand" onClick={sort('fase')}>
                  Fase <FontAwesomeIcon icon={getSortIconByFieldName('fase')} />
                </th>
                <th className="hand" onClick={sort('hoogstetarief')}>
                  Hoogstetarief <FontAwesomeIcon icon={getSortIconByFieldName('hoogstetarief')} />
                </th>
                <th className="hand" onClick={sort('laagstetarief')}>
                  Laagstetarief <FontAwesomeIcon icon={getSortIconByFieldName('laagstetarief')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('perceel')}>
                  Perceel <FontAwesomeIcon icon={getSortIconByFieldName('perceel')} />
                </th>
                <th className="hand" onClick={sort('procedure')}>
                  Procedure <FontAwesomeIcon icon={getSortIconByFieldName('procedure')} />
                </th>
                <th className="hand" onClick={sort('projectnaam')}>
                  Projectnaam <FontAwesomeIcon icon={getSortIconByFieldName('projectnaam')} />
                </th>
                <th className="hand" onClick={sort('projectreferentie')}>
                  Projectreferentie <FontAwesomeIcon icon={getSortIconByFieldName('projectreferentie')} />
                </th>
                <th className="hand" onClick={sort('publicatie')}>
                  Publicatie <FontAwesomeIcon icon={getSortIconByFieldName('publicatie')} />
                </th>
                <th className="hand" onClick={sort('referentie')}>
                  Referentie <FontAwesomeIcon icon={getSortIconByFieldName('referentie')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('titel')}>
                  Titel <FontAwesomeIcon icon={getSortIconByFieldName('titel')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aanbestedinginhuurList.map((aanbestedinginhuur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aanbestedinginhuur/${aanbestedinginhuur.id}`} color="link" size="sm">
                      {aanbestedinginhuur.id}
                    </Button>
                  </td>
                  <td>{aanbestedinginhuur.aanvraaggesloten}</td>
                  <td>{aanbestedinginhuur.aanvraagnummer}</td>
                  <td>{aanbestedinginhuur.datumcreatie}</td>
                  <td>{aanbestedinginhuur.datumopeningkluis}</td>
                  <td>{aanbestedinginhuur.datumsluiting}</td>
                  <td>{aanbestedinginhuur.datumverzending}</td>
                  <td>{aanbestedinginhuur.fase}</td>
                  <td>{aanbestedinginhuur.hoogstetarief}</td>
                  <td>{aanbestedinginhuur.laagstetarief}</td>
                  <td>{aanbestedinginhuur.omschrijving}</td>
                  <td>{aanbestedinginhuur.perceel}</td>
                  <td>{aanbestedinginhuur.procedure}</td>
                  <td>{aanbestedinginhuur.projectnaam}</td>
                  <td>{aanbestedinginhuur.projectreferentie}</td>
                  <td>{aanbestedinginhuur.publicatie}</td>
                  <td>{aanbestedinginhuur.referentie}</td>
                  <td>{aanbestedinginhuur.status}</td>
                  <td>{aanbestedinginhuur.titel}</td>
                  <td>{aanbestedinginhuur.type}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/aanbestedinginhuur/${aanbestedinginhuur.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aanbestedinginhuur/${aanbestedinginhuur.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aanbestedinginhuur/${aanbestedinginhuur.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aanbestedinginhuurs found</div>
        )}
      </div>
    </div>
  );
};

export default Aanbestedinginhuur;
