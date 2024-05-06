import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './stremming.reducer';

export const Stremming = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const stremmingList = useAppSelector(state => state.stremming.entities);
  const loading = useAppSelector(state => state.stremming.loading);

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
      <h2 id="stremming-heading" data-cy="StremmingHeading">
        Stremmings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/stremming/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Stremming
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {stremmingList && stremmingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalgehinderden')}>
                  Aantalgehinderden <FontAwesomeIcon icon={getSortIconByFieldName('aantalgehinderden')} />
                </th>
                <th className="hand" onClick={sort('datumaanmelding')}>
                  Datumaanmelding <FontAwesomeIcon icon={getSortIconByFieldName('datumaanmelding')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumwijziging')}>
                  Datumwijziging <FontAwesomeIcon icon={getSortIconByFieldName('datumwijziging')} />
                </th>
                <th className="hand" onClick={sort('delentoegestaan')}>
                  Delentoegestaan <FontAwesomeIcon icon={getSortIconByFieldName('delentoegestaan')} />
                </th>
                <th className="hand" onClick={sort('geschiktvoorpublicatie')}>
                  Geschiktvoorpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('geschiktvoorpublicatie')} />
                </th>
                <th className="hand" onClick={sort('hinderklasse')}>
                  Hinderklasse <FontAwesomeIcon icon={getSortIconByFieldName('hinderklasse')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th>
                  Betreft Wegdeel <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ingevoerddoor Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gewijzigddoor Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {stremmingList.map((stremming, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/stremming/${stremming.id}`} color="link" size="sm">
                      {stremming.id}
                    </Button>
                  </td>
                  <td>{stremming.aantalgehinderden}</td>
                  <td>{stremming.datumaanmelding}</td>
                  <td>{stremming.datumeinde}</td>
                  <td>{stremming.datumstart}</td>
                  <td>{stremming.datumwijziging}</td>
                  <td>{stremming.delentoegestaan ? 'true' : 'false'}</td>
                  <td>{stremming.geschiktvoorpublicatie ? 'true' : 'false'}</td>
                  <td>{stremming.hinderklasse}</td>
                  <td>{stremming.locatie}</td>
                  <td>{stremming.naam}</td>
                  <td>{stremming.status}</td>
                  <td>
                    {stremming.betreftWegdeels
                      ? stremming.betreftWegdeels.map((val, j) => (
                          <span key={j}>
                            <Link to={`/wegdeel/${val.id}`}>{val.id}</Link>
                            {j === stremming.betreftWegdeels.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {stremming.ingevoerddoorMedewerker ? (
                      <Link to={`/medewerker/${stremming.ingevoerddoorMedewerker.id}`}>{stremming.ingevoerddoorMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {stremming.gewijzigddoorMedewerker ? (
                      <Link to={`/medewerker/${stremming.gewijzigddoorMedewerker.id}`}>{stremming.gewijzigddoorMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/stremming/${stremming.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/stremming/${stremming.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/stremming/${stremming.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Stremmings found</div>
        )}
      </div>
    </div>
  );
};

export default Stremming;
