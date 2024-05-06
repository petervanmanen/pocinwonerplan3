import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './moormelding.reducer';

export const Moormelding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const moormeldingList = useAppSelector(state => state.moormelding.entities);
  const loading = useAppSelector(state => state.moormelding.loading);

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
      <h2 id="moormelding-heading" data-cy="MoormeldingHeading">
        Moormeldings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/moormelding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Moormelding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {moormeldingList && moormeldingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresaanduiding')}>
                  Adresaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('adresaanduiding')} />
                </th>
                <th className="hand" onClick={sort('datumaanmelding')}>
                  Datumaanmelding <FontAwesomeIcon icon={getSortIconByFieldName('datumaanmelding')} />
                </th>
                <th className="hand" onClick={sort('datumgoedkeuring')}>
                  Datumgoedkeuring <FontAwesomeIcon icon={getSortIconByFieldName('datumgoedkeuring')} />
                </th>
                <th className="hand" onClick={sort('eindtijd')}>
                  Eindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eindtijd')} />
                </th>
                <th className="hand" onClick={sort('goedgekeurd')}>
                  Goedgekeurd <FontAwesomeIcon icon={getSortIconByFieldName('goedgekeurd')} />
                </th>
                <th className="hand" onClick={sort('herstelwerkzaamhedenvereist')}>
                  Herstelwerkzaamhedenvereist <FontAwesomeIcon icon={getSortIconByFieldName('herstelwerkzaamhedenvereist')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingherstelwerkzaamheden')}>
                  Omschrijvingherstelwerkzaamheden <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingherstelwerkzaamheden')} />
                </th>
                <th className="hand" onClick={sort('publiceren')}>
                  Publiceren <FontAwesomeIcon icon={getSortIconByFieldName('publiceren')} />
                </th>
                <th className="hand" onClick={sort('starttijd')}>
                  Starttijd <FontAwesomeIcon icon={getSortIconByFieldName('starttijd')} />
                </th>
                <th className="hand" onClick={sort('wegbeheerder')}>
                  Wegbeheerder <FontAwesomeIcon icon={getSortIconByFieldName('wegbeheerder')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {moormeldingList.map((moormelding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/moormelding/${moormelding.id}`} color="link" size="sm">
                      {moormelding.id}
                    </Button>
                  </td>
                  <td>{moormelding.adresaanduiding}</td>
                  <td>{moormelding.datumaanmelding}</td>
                  <td>{moormelding.datumgoedkeuring}</td>
                  <td>{moormelding.eindtijd}</td>
                  <td>{moormelding.goedgekeurd ? 'true' : 'false'}</td>
                  <td>{moormelding.herstelwerkzaamhedenvereist ? 'true' : 'false'}</td>
                  <td>{moormelding.omschrijvingherstelwerkzaamheden}</td>
                  <td>{moormelding.publiceren ? 'true' : 'false'}</td>
                  <td>{moormelding.starttijd}</td>
                  <td>{moormelding.wegbeheerder}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/moormelding/${moormelding.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/moormelding/${moormelding.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/moormelding/${moormelding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Moormeldings found</div>
        )}
      </div>
    </div>
  );
};

export default Moormelding;
