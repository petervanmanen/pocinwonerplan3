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

import { getEntities } from './ligplaats.reducer';

export const Ligplaats = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ligplaatsList = useAppSelector(state => state.ligplaats.entities);
  const loading = useAppSelector(state => state.ligplaats.loading);

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
      <h2 id="ligplaats-heading" data-cy="LigplaatsHeading">
        Ligplaats
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ligplaats/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ligplaats
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ligplaatsList && ligplaatsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheid')}>
                  Datumbegingeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumingang')}>
                  Datumingang <FontAwesomeIcon icon={getSortIconByFieldName('datumingang')} />
                </th>
                <th className="hand" onClick={sort('documentdatum')}>
                  Documentdatum <FontAwesomeIcon icon={getSortIconByFieldName('documentdatum')} />
                </th>
                <th className="hand" onClick={sort('documentnummer')}>
                  Documentnummer <FontAwesomeIcon icon={getSortIconByFieldName('documentnummer')} />
                </th>
                <th className="hand" onClick={sort('geconstateerd')}>
                  Geconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('geconstateerd')} />
                </th>
                <th className="hand" onClick={sort('geometrie')}>
                  Geometrie <FontAwesomeIcon icon={getSortIconByFieldName('geometrie')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('inonderzoek')}>
                  Inonderzoek <FontAwesomeIcon icon={getSortIconByFieldName('inonderzoek')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('versie')}>
                  Versie <FontAwesomeIcon icon={getSortIconByFieldName('versie')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ligplaatsList.map((ligplaats, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ligplaats/${ligplaats.id}`} color="link" size="sm">
                      {ligplaats.id}
                    </Button>
                  </td>
                  <td>
                    {ligplaats.datumbegingeldigheid ? (
                      <TextFormat type="date" value={ligplaats.datumbegingeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {ligplaats.datumeinde ? <TextFormat type="date" value={ligplaats.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {ligplaats.datumeindegeldigheid ? (
                      <TextFormat type="date" value={ligplaats.datumeindegeldigheid} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {ligplaats.datumingang ? <TextFormat type="date" value={ligplaats.datumingang} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {ligplaats.documentdatum ? (
                      <TextFormat type="date" value={ligplaats.documentdatum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{ligplaats.documentnummer}</td>
                  <td>{ligplaats.geconstateerd}</td>
                  <td>{ligplaats.geometrie}</td>
                  <td>{ligplaats.identificatie}</td>
                  <td>{ligplaats.inonderzoek ? 'true' : 'false'}</td>
                  <td>{ligplaats.status}</td>
                  <td>{ligplaats.versie}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ligplaats/${ligplaats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/ligplaats/${ligplaats.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ligplaats/${ligplaats.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ligplaats found</div>
        )}
      </div>
    </div>
  );
};

export default Ligplaats;
