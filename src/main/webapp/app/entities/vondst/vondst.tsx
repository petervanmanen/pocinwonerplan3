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

import { getEntities } from './vondst.reducer';

export const Vondst = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vondstList = useAppSelector(state => state.vondst.entities);
  const loading = useAppSelector(state => state.vondst.loading);

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
      <h2 id="vondst-heading" data-cy="VondstHeading">
        Vondsts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vondst/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vondst
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vondstList && vondstList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keyvulling')}>
                  Keyvulling <FontAwesomeIcon icon={getSortIconByFieldName('keyvulling')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('omstandigheden')}>
                  Omstandigheden <FontAwesomeIcon icon={getSortIconByFieldName('omstandigheden')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th className="hand" onClick={sort('putnummer')}>
                  Putnummer <FontAwesomeIcon icon={getSortIconByFieldName('putnummer')} />
                </th>
                <th className="hand" onClick={sort('spoornummer')}>
                  Spoornummer <FontAwesomeIcon icon={getSortIconByFieldName('spoornummer')} />
                </th>
                <th className="hand" onClick={sort('vlaknummer')}>
                  Vlaknummer <FontAwesomeIcon icon={getSortIconByFieldName('vlaknummer')} />
                </th>
                <th className="hand" onClick={sort('vondstnummer')}>
                  Vondstnummer <FontAwesomeIcon icon={getSortIconByFieldName('vondstnummer')} />
                </th>
                <th className="hand" onClick={sort('vullingnummer')}>
                  Vullingnummer <FontAwesomeIcon icon={getSortIconByFieldName('vullingnummer')} />
                </th>
                <th className="hand" onClick={sort('xcoordinaat')}>
                  Xcoordinaat <FontAwesomeIcon icon={getSortIconByFieldName('xcoordinaat')} />
                </th>
                <th className="hand" onClick={sort('ycoordinaat')}>
                  Ycoordinaat <FontAwesomeIcon icon={getSortIconByFieldName('ycoordinaat')} />
                </th>
                <th>
                  Heeft Vulling <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vondstList.map((vondst, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vondst/${vondst.id}`} color="link" size="sm">
                      {vondst.id}
                    </Button>
                  </td>
                  <td>{vondst.datum ? <TextFormat type="date" value={vondst.datum} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{vondst.key}</td>
                  <td>{vondst.keyvulling}</td>
                  <td>{vondst.omschrijving}</td>
                  <td>{vondst.omstandigheden}</td>
                  <td>{vondst.projectcd}</td>
                  <td>{vondst.putnummer}</td>
                  <td>{vondst.spoornummer}</td>
                  <td>{vondst.vlaknummer}</td>
                  <td>{vondst.vondstnummer}</td>
                  <td>{vondst.vullingnummer}</td>
                  <td>{vondst.xcoordinaat}</td>
                  <td>{vondst.ycoordinaat}</td>
                  <td>{vondst.heeftVulling ? <Link to={`/vulling/${vondst.heeftVulling.id}`}>{vondst.heeftVulling.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vondst/${vondst.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vondst/${vondst.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vondst/${vondst.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vondsts found</div>
        )}
      </div>
    </div>
  );
};

export default Vondst;
