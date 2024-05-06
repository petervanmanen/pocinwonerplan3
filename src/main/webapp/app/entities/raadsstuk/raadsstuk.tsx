import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './raadsstuk.reducer';

export const Raadsstuk = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const raadsstukList = useAppSelector(state => state.raadsstuk.entities);
  const loading = useAppSelector(state => state.raadsstuk.loading);

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
      <h2 id="raadsstuk-heading" data-cy="RaadsstukHeading">
        Raadsstuks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/raadsstuk/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Raadsstuk
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {raadsstukList && raadsstukList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('besloten')}>
                  Besloten <FontAwesomeIcon icon={getSortIconByFieldName('besloten')} />
                </th>
                <th className="hand" onClick={sort('datumexpiratie')}>
                  Datumexpiratie <FontAwesomeIcon icon={getSortIconByFieldName('datumexpiratie')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumregistratie')}>
                  Datumregistratie <FontAwesomeIcon icon={getSortIconByFieldName('datumregistratie')} />
                </th>
                <th className="hand" onClick={sort('typeraadsstuk')}>
                  Typeraadsstuk <FontAwesomeIcon icon={getSortIconByFieldName('typeraadsstuk')} />
                </th>
                <th>
                  Heeft Taakveld <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Behandelt Agendapunt <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Hoortbij Programma <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtbehandeldin Vergadering <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Categorie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Hoortbij Dossier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Indiener <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {raadsstukList.map((raadsstuk, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/raadsstuk/${raadsstuk.id}`} color="link" size="sm">
                      {raadsstuk.id}
                    </Button>
                  </td>
                  <td>{raadsstuk.besloten}</td>
                  <td>{raadsstuk.datumexpiratie}</td>
                  <td>{raadsstuk.datumpublicatie}</td>
                  <td>{raadsstuk.datumregistratie}</td>
                  <td>{raadsstuk.typeraadsstuk}</td>
                  <td>
                    {raadsstuk.heeftTaakveld ? (
                      <Link to={`/taakveld/${raadsstuk.heeftTaakveld.id}`}>{raadsstuk.heeftTaakveld.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {raadsstuk.behandeltAgendapunts
                      ? raadsstuk.behandeltAgendapunts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/agendapunt/${val.id}`}>{val.id}</Link>
                            {j === raadsstuk.behandeltAgendapunts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {raadsstuk.hoortbijProgrammas
                      ? raadsstuk.hoortbijProgrammas.map((val, j) => (
                          <span key={j}>
                            <Link to={`/programma/${val.id}`}>{val.id}</Link>
                            {j === raadsstuk.hoortbijProgrammas.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {raadsstuk.wordtbehandeldinVergaderings
                      ? raadsstuk.wordtbehandeldinVergaderings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/vergadering/${val.id}`}>{val.id}</Link>
                            {j === raadsstuk.wordtbehandeldinVergaderings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {raadsstuk.heeftCategorie ? (
                      <Link to={`/categorie/${raadsstuk.heeftCategorie.id}`}>{raadsstuk.heeftCategorie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {raadsstuk.hoortbijDossiers
                      ? raadsstuk.hoortbijDossiers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/dossier/${val.id}`}>{val.id}</Link>
                            {j === raadsstuk.hoortbijDossiers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {raadsstuk.heeftIndieners
                      ? raadsstuk.heeftIndieners.map((val, j) => (
                          <span key={j}>
                            <Link to={`/indiener/${val.id}`}>{val.id}</Link>
                            {j === raadsstuk.heeftIndieners.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/raadsstuk/${raadsstuk.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/raadsstuk/${raadsstuk.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/raadsstuk/${raadsstuk.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Raadsstuks found</div>
        )}
      </div>
    </div>
  );
};

export default Raadsstuk;
