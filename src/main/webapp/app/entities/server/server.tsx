import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './server.reducer';

export const Server = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const serverList = useAppSelector(state => state.server.entities);
  const loading = useAppSelector(state => state.server.loading);

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
      <h2 id="server-heading" data-cy="ServerHeading">
        Servers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/server/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Server
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {serverList && serverList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('actief')}>
                  Actief <FontAwesomeIcon icon={getSortIconByFieldName('actief')} />
                </th>
                <th className="hand" onClick={sort('ipadres')}>
                  Ipadres <FontAwesomeIcon icon={getSortIconByFieldName('ipadres')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('organisatie')}>
                  Organisatie <FontAwesomeIcon icon={getSortIconByFieldName('organisatie')} />
                </th>
                <th className="hand" onClick={sort('serienummer')}>
                  Serienummer <FontAwesomeIcon icon={getSortIconByFieldName('serienummer')} />
                </th>
                <th className="hand" onClick={sort('serverid')}>
                  Serverid <FontAwesomeIcon icon={getSortIconByFieldName('serverid')} />
                </th>
                <th className="hand" onClick={sort('servertype')}>
                  Servertype <FontAwesomeIcon icon={getSortIconByFieldName('servertype')} />
                </th>
                <th className="hand" onClick={sort('vlan')}>
                  Vlan <FontAwesomeIcon icon={getSortIconByFieldName('vlan')} />
                </th>
                <th>
                  Heeftleverancier Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serverList.map((server, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/server/${server.id}`} color="link" size="sm">
                      {server.id}
                    </Button>
                  </td>
                  <td>{server.actief ? 'true' : 'false'}</td>
                  <td>{server.ipadres}</td>
                  <td>{server.locatie}</td>
                  <td>{server.organisatie}</td>
                  <td>{server.serienummer}</td>
                  <td>{server.serverid}</td>
                  <td>{server.servertype}</td>
                  <td>{server.vlan}</td>
                  <td>
                    {server.heeftleverancierLeverancier ? (
                      <Link to={`/leverancier/${server.heeftleverancierLeverancier.id}`}>{server.heeftleverancierLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/server/${server.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/server/${server.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/server/${server.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Servers found</div>
        )}
      </div>
    </div>
  );
};

export default Server;
