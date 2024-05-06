import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './gunning.reducer';

export const Gunning = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const gunningList = useAppSelector(state => state.gunning.entities);
  const loading = useAppSelector(state => state.gunning.loading);

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
      <h2 id="gunning-heading" data-cy="GunningHeading">
        Gunnings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/gunning/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Gunning
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gunningList && gunningList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bericht')}>
                  Bericht <FontAwesomeIcon icon={getSortIconByFieldName('bericht')} />
                </th>
                <th className="hand" onClick={sort('datumgunning')}>
                  Datumgunning <FontAwesomeIcon icon={getSortIconByFieldName('datumgunning')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumvoorlopigegunning')}>
                  Datumvoorlopigegunning <FontAwesomeIcon icon={getSortIconByFieldName('datumvoorlopigegunning')} />
                </th>
                <th className="hand" onClick={sort('gegundeprijs')}>
                  Gegundeprijs <FontAwesomeIcon icon={getSortIconByFieldName('gegundeprijs')} />
                </th>
                <th>
                  Betreft Inschrijving <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Kandidaat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Offerte <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Inhuur Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gunningList.map((gunning, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gunning/${gunning.id}`} color="link" size="sm">
                      {gunning.id}
                    </Button>
                  </td>
                  <td>{gunning.bericht}</td>
                  <td>{gunning.datumgunning}</td>
                  <td>{gunning.datumpublicatie}</td>
                  <td>{gunning.datumvoorlopigegunning}</td>
                  <td>{gunning.gegundeprijs}</td>
                  <td>
                    {gunning.betreftInschrijving ? (
                      <Link to={`/inschrijving/${gunning.betreftInschrijving.id}`}>{gunning.betreftInschrijving.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {gunning.betreftKandidaat ? (
                      <Link to={`/kandidaat/${gunning.betreftKandidaat.id}`}>{gunning.betreftKandidaat.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {gunning.betreftOfferte ? <Link to={`/offerte/${gunning.betreftOfferte.id}`}>{gunning.betreftOfferte.id}</Link> : ''}
                  </td>
                  <td>
                    {gunning.inhuurMedewerker ? (
                      <Link to={`/medewerker/${gunning.inhuurMedewerker.id}`}>{gunning.inhuurMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gunning/${gunning.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/gunning/${gunning.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/gunning/${gunning.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Gunnings found</div>
        )}
      </div>
    </div>
  );
};

export default Gunning;
