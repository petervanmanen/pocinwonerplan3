import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './foto.reducer';

export const Foto = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const fotoList = useAppSelector(state => state.foto.entities);
  const loading = useAppSelector(state => state.foto.loading);

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
      <h2 id="foto-heading" data-cy="FotoHeading">
        Fotos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/foto/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Foto
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fotoList && fotoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bestandsgrootte')}>
                  Bestandsgrootte <FontAwesomeIcon icon={getSortIconByFieldName('bestandsgrootte')} />
                </th>
                <th className="hand" onClick={sort('bestandsnaam')}>
                  Bestandsnaam <FontAwesomeIcon icon={getSortIconByFieldName('bestandsnaam')} />
                </th>
                <th className="hand" onClick={sort('bestandstype')}>
                  Bestandstype <FontAwesomeIcon icon={getSortIconByFieldName('bestandstype')} />
                </th>
                <th className="hand" onClick={sort('datumtijd')}>
                  Datumtijd <FontAwesomeIcon icon={getSortIconByFieldName('datumtijd')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('pixelsx')}>
                  Pixelsx <FontAwesomeIcon icon={getSortIconByFieldName('pixelsx')} />
                </th>
                <th className="hand" onClick={sort('pixelsy')}>
                  Pixelsy <FontAwesomeIcon icon={getSortIconByFieldName('pixelsy')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fotoList.map((foto, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/foto/${foto.id}`} color="link" size="sm">
                      {foto.id}
                    </Button>
                  </td>
                  <td>{foto.bestandsgrootte}</td>
                  <td>{foto.bestandsnaam}</td>
                  <td>{foto.bestandstype}</td>
                  <td>{foto.datumtijd}</td>
                  <td>{foto.locatie}</td>
                  <td>{foto.pixelsx}</td>
                  <td>{foto.pixelsy}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/foto/${foto.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/foto/${foto.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/foto/${foto.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Fotos found</div>
        )}
      </div>
    </div>
  );
};

export default Foto;