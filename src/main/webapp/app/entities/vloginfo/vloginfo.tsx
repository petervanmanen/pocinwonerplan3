import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vloginfo.reducer';

export const Vloginfo = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vloginfoList = useAppSelector(state => state.vloginfo.entities);
  const loading = useAppSelector(state => state.vloginfo.loading);

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
      <h2 id="vloginfo-heading" data-cy="VloginfoHeading">
        Vloginfos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vloginfo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vloginfo
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vloginfoList && vloginfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('detectieverkeer')}>
                  Detectieverkeer <FontAwesomeIcon icon={getSortIconByFieldName('detectieverkeer')} />
                </th>
                <th className="hand" onClick={sort('eindegroen')}>
                  Eindegroen <FontAwesomeIcon icon={getSortIconByFieldName('eindegroen')} />
                </th>
                <th className="hand" onClick={sort('snelheid')}>
                  Snelheid <FontAwesomeIcon icon={getSortIconByFieldName('snelheid')} />
                </th>
                <th className="hand" onClick={sort('startgroen')}>
                  Startgroen <FontAwesomeIcon icon={getSortIconByFieldName('startgroen')} />
                </th>
                <th className="hand" onClick={sort('tijdstip')}>
                  Tijdstip <FontAwesomeIcon icon={getSortIconByFieldName('tijdstip')} />
                </th>
                <th className="hand" onClick={sort('verkeerwilgroen')}>
                  Verkeerwilgroen <FontAwesomeIcon icon={getSortIconByFieldName('verkeerwilgroen')} />
                </th>
                <th className="hand" onClick={sort('wachttijd')}>
                  Wachttijd <FontAwesomeIcon icon={getSortIconByFieldName('wachttijd')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vloginfoList.map((vloginfo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vloginfo/${vloginfo.id}`} color="link" size="sm">
                      {vloginfo.id}
                    </Button>
                  </td>
                  <td>{vloginfo.detectieverkeer}</td>
                  <td>{vloginfo.eindegroen ? 'true' : 'false'}</td>
                  <td>{vloginfo.snelheid}</td>
                  <td>{vloginfo.startgroen ? 'true' : 'false'}</td>
                  <td>{vloginfo.tijdstip}</td>
                  <td>{vloginfo.verkeerwilgroen ? 'true' : 'false'}</td>
                  <td>{vloginfo.wachttijd}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vloginfo/${vloginfo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vloginfo/${vloginfo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vloginfo/${vloginfo.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vloginfos found</div>
        )}
      </div>
    </div>
  );
};

export default Vloginfo;
