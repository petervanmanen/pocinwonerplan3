import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './archiefstuk.reducer';

export const Archiefstuk = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const archiefstukList = useAppSelector(state => state.archiefstuk.entities);
  const loading = useAppSelector(state => state.archiefstuk.loading);

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
      <h2 id="archiefstuk-heading" data-cy="ArchiefstukHeading">
        Archiefstuks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/archiefstuk/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Archiefstuk
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {archiefstukList && archiefstukList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('inventarisnummer')}>
                  Inventarisnummer <FontAwesomeIcon icon={getSortIconByFieldName('inventarisnummer')} />
                </th>
                <th className="hand" onClick={sort('omvang')}>
                  Omvang <FontAwesomeIcon icon={getSortIconByFieldName('omvang')} />
                </th>
                <th className="hand" onClick={sort('openbaarheidsbeperking')}>
                  Openbaarheidsbeperking <FontAwesomeIcon icon={getSortIconByFieldName('openbaarheidsbeperking')} />
                </th>
                <th className="hand" onClick={sort('trefwoorden')}>
                  Trefwoorden <FontAwesomeIcon icon={getSortIconByFieldName('trefwoorden')} />
                </th>
                <th className="hand" onClick={sort('uiterlijkevorm')}>
                  Uiterlijkevorm <FontAwesomeIcon icon={getSortIconByFieldName('uiterlijkevorm')} />
                </th>
                <th>
                  Isonderdeelvan Archief <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Uitgever <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Vindplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Ordeningsschema <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Stamtuit Periode <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Indeling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voor Aanvraag <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {archiefstukList.map((archiefstuk, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/archiefstuk/${archiefstuk.id}`} color="link" size="sm">
                      {archiefstuk.id}
                    </Button>
                  </td>
                  <td>{archiefstuk.beschrijving}</td>
                  <td>{archiefstuk.inventarisnummer}</td>
                  <td>{archiefstuk.omvang}</td>
                  <td>{archiefstuk.openbaarheidsbeperking}</td>
                  <td>{archiefstuk.trefwoorden}</td>
                  <td>{archiefstuk.uiterlijkevorm}</td>
                  <td>
                    {archiefstuk.isonderdeelvanArchief ? (
                      <Link to={`/archief/${archiefstuk.isonderdeelvanArchief.id}`}>{archiefstuk.isonderdeelvanArchief.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {archiefstuk.heeftUitgever ? (
                      <Link to={`/uitgever/${archiefstuk.heeftUitgever.id}`}>{archiefstuk.heeftUitgever.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {archiefstuk.heeftVindplaats ? (
                      <Link to={`/vindplaats/${archiefstuk.heeftVindplaats.id}`}>{archiefstuk.heeftVindplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {archiefstuk.heeftOrdeningsschemas
                      ? archiefstuk.heeftOrdeningsschemas.map((val, j) => (
                          <span key={j}>
                            <Link to={`/ordeningsschema/${val.id}`}>{val.id}</Link>
                            {j === archiefstuk.heeftOrdeningsschemas.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {archiefstuk.stamtuitPeriodes
                      ? archiefstuk.stamtuitPeriodes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/periode/${val.id}`}>{val.id}</Link>
                            {j === archiefstuk.stamtuitPeriodes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {archiefstuk.valtbinnenIndeling ? (
                      <Link to={`/indeling/${archiefstuk.valtbinnenIndeling.id}`}>{archiefstuk.valtbinnenIndeling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {archiefstuk.voorAanvraags
                      ? archiefstuk.voorAanvraags.map((val, j) => (
                          <span key={j}>
                            <Link to={`/aanvraag/${val.id}`}>{val.id}</Link>
                            {j === archiefstuk.voorAanvraags.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/archiefstuk/${archiefstuk.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/archiefstuk/${archiefstuk.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/archiefstuk/${archiefstuk.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Archiefstuks found</div>
        )}
      </div>
    </div>
  );
};

export default Archiefstuk;
