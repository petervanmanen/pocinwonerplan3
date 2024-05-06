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

import { getEntities } from './buurt.reducer';

export const Buurt = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const buurtList = useAppSelector(state => state.buurt.entities);
  const loading = useAppSelector(state => state.buurt.loading);

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
      <h2 id="buurt-heading" data-cy="BuurtHeading">
        Buurts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/buurt/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Buurt
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {buurtList && buurtList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('buurtcode')}>
                  Buurtcode <FontAwesomeIcon icon={getSortIconByFieldName('buurtcode')} />
                </th>
                <th className="hand" onClick={sort('buurtgeometrie')}>
                  Buurtgeometrie <FontAwesomeIcon icon={getSortIconByFieldName('buurtgeometrie')} />
                </th>
                <th className="hand" onClick={sort('buurtnaam')}>
                  Buurtnaam <FontAwesomeIcon icon={getSortIconByFieldName('buurtnaam')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidbuurt')}>
                  Datumbegingeldigheidbuurt <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidbuurt')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidbuurt')}>
                  Datumeindegeldigheidbuurt <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidbuurt')} />
                </th>
                <th className="hand" onClick={sort('datumingang')}>
                  Datumingang <FontAwesomeIcon icon={getSortIconByFieldName('datumingang')} />
                </th>
                <th className="hand" onClick={sort('geconstateerd')}>
                  Geconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('geconstateerd')} />
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
                <th>
                  Ligtin Areaal <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {buurtList.map((buurt, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/buurt/${buurt.id}`} color="link" size="sm">
                      {buurt.id}
                    </Button>
                  </td>
                  <td>{buurt.buurtcode}</td>
                  <td>{buurt.buurtgeometrie}</td>
                  <td>{buurt.buurtnaam}</td>
                  <td>
                    {buurt.datumbegingeldigheidbuurt ? (
                      <TextFormat type="date" value={buurt.datumbegingeldigheidbuurt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{buurt.datumeinde ? <TextFormat type="date" value={buurt.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {buurt.datumeindegeldigheidbuurt ? (
                      <TextFormat type="date" value={buurt.datumeindegeldigheidbuurt} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{buurt.datumingang ? <TextFormat type="date" value={buurt.datumingang} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{buurt.geconstateerd ? 'true' : 'false'}</td>
                  <td>{buurt.identificatie}</td>
                  <td>{buurt.inonderzoek ? 'true' : 'false'}</td>
                  <td>{buurt.status}</td>
                  <td>{buurt.versie}</td>
                  <td>
                    {buurt.ligtinAreaals
                      ? buurt.ligtinAreaals.map((val, j) => (
                          <span key={j}>
                            <Link to={`/areaal/${val.id}`}>{val.id}</Link>
                            {j === buurt.ligtinAreaals.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/buurt/${buurt.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/buurt/${buurt.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/buurt/${buurt.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Buurts found</div>
        )}
      </div>
    </div>
  );
};

export default Buurt;
