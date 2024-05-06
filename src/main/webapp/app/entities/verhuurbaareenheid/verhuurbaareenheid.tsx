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

import { getEntities } from './verhuurbaareenheid.reducer';

export const Verhuurbaareenheid = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verhuurbaareenheidList = useAppSelector(state => state.verhuurbaareenheid.entities);
  const loading = useAppSelector(state => state.verhuurbaareenheid.loading);

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
      <h2 id="verhuurbaareenheid-heading" data-cy="VerhuurbaareenheidHeading">
        Verhuurbaareenheids
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/verhuurbaareenheid/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verhuurbaareenheid
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verhuurbaareenheidList && verhuurbaareenheidList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th className="hand" onClick={sort('afmeting')}>
                  Afmeting <FontAwesomeIcon icon={getSortIconByFieldName('afmeting')} />
                </th>
                <th className="hand" onClick={sort('bezetting')}>
                  Bezetting <FontAwesomeIcon icon={getSortIconByFieldName('bezetting')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumwerkelijkbegin')}>
                  Datumwerkelijkbegin <FontAwesomeIcon icon={getSortIconByFieldName('datumwerkelijkbegin')} />
                </th>
                <th className="hand" onClick={sort('datumwerkelijkeinde')}>
                  Datumwerkelijkeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumwerkelijkeinde')} />
                </th>
                <th className="hand" onClick={sort('huurprijs')}>
                  Huurprijs <FontAwesomeIcon icon={getSortIconByFieldName('huurprijs')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('nettoomtrek')}>
                  Nettoomtrek <FontAwesomeIcon icon={getSortIconByFieldName('nettoomtrek')} />
                </th>
                <th className="hand" onClick={sort('nettooppervlak')}>
                  Nettooppervlak <FontAwesomeIcon icon={getSortIconByFieldName('nettooppervlak')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verhuurbaareenheidList.map((verhuurbaareenheid, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verhuurbaareenheid/${verhuurbaareenheid.id}`} color="link" size="sm">
                      {verhuurbaareenheid.id}
                    </Button>
                  </td>
                  <td>{verhuurbaareenheid.adres}</td>
                  <td>{verhuurbaareenheid.afmeting}</td>
                  <td>{verhuurbaareenheid.bezetting}</td>
                  <td>
                    {verhuurbaareenheid.datumeinde ? (
                      <TextFormat type="date" value={verhuurbaareenheid.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verhuurbaareenheid.datumstart ? (
                      <TextFormat type="date" value={verhuurbaareenheid.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verhuurbaareenheid.datumwerkelijkbegin ? (
                      <TextFormat type="date" value={verhuurbaareenheid.datumwerkelijkbegin} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {verhuurbaareenheid.datumwerkelijkeinde ? (
                      <TextFormat type="date" value={verhuurbaareenheid.datumwerkelijkeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verhuurbaareenheid.huurprijs}</td>
                  <td>{verhuurbaareenheid.identificatie}</td>
                  <td>{verhuurbaareenheid.naam}</td>
                  <td>{verhuurbaareenheid.nettoomtrek}</td>
                  <td>{verhuurbaareenheid.nettooppervlak}</td>
                  <td>{verhuurbaareenheid.opmerkingen}</td>
                  <td>{verhuurbaareenheid.type}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/verhuurbaareenheid/${verhuurbaareenheid.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verhuurbaareenheid/${verhuurbaareenheid.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verhuurbaareenheid/${verhuurbaareenheid.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verhuurbaareenheids found</div>
        )}
      </div>
    </div>
  );
};

export default Verhuurbaareenheid;
