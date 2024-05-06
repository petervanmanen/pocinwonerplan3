import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './client.reducer';

export const Client = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const clientList = useAppSelector(state => state.client.entities);
  const loading = useAppSelector(state => state.client.loading);

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
      <h2 id="client-heading" data-cy="ClientHeading">
        Clients
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/client/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Client
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clientList && clientList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('gezagsdragergekend')}>
                  Gezagsdragergekend <FontAwesomeIcon icon={getSortIconByFieldName('gezagsdragergekend')} />
                </th>
                <th className="hand" onClick={sort('juridischestatus')}>
                  Juridischestatus <FontAwesomeIcon icon={getSortIconByFieldName('juridischestatus')} />
                </th>
                <th className="hand" onClick={sort('wettelijkevertegenwoordiging')}>
                  Wettelijkevertegenwoordiging <FontAwesomeIcon icon={getSortIconByFieldName('wettelijkevertegenwoordiging')} />
                </th>
                <th>
                  Heeft Participatiedossier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftvoorziening Inkomensvoorziening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnendoelgroep Doelgroep <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftrelatie Relatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voorzieningbijstandspartij Inkomensvoorziening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Maaktonderdeeluitvan Huishouden <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heefttaalniveau Taalniveau <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Ondersteuntclient Clientbegeleider <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clientList.map((client, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/client/${client.id}`} color="link" size="sm">
                      {client.id}
                    </Button>
                  </td>
                  <td>{client.code}</td>
                  <td>{client.gezagsdragergekend ? 'true' : 'false'}</td>
                  <td>{client.juridischestatus}</td>
                  <td>{client.wettelijkevertegenwoordiging}</td>
                  <td>
                    {client.heeftParticipatiedossier ? (
                      <Link to={`/participatiedossier/${client.heeftParticipatiedossier.id}`}>{client.heeftParticipatiedossier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {client.heeftvoorzieningInkomensvoorziening ? (
                      <Link to={`/inkomensvoorziening/${client.heeftvoorzieningInkomensvoorziening.id}`}>
                        {client.heeftvoorzieningInkomensvoorziening.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {client.valtbinnendoelgroepDoelgroep ? (
                      <Link to={`/doelgroep/${client.valtbinnendoelgroepDoelgroep.id}`}>{client.valtbinnendoelgroepDoelgroep.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {client.heeftrelatieRelaties
                      ? client.heeftrelatieRelaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/relatie/${val.id}`}>{val.id}</Link>
                            {j === client.heeftrelatieRelaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {client.voorzieningbijstandspartijInkomensvoorzienings
                      ? client.voorzieningbijstandspartijInkomensvoorzienings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/inkomensvoorziening/${val.id}`}>{val.id}</Link>
                            {j === client.voorzieningbijstandspartijInkomensvoorzienings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {client.maaktonderdeeluitvanHuishoudens
                      ? client.maaktonderdeeluitvanHuishoudens.map((val, j) => (
                          <span key={j}>
                            <Link to={`/huishouden/${val.id}`}>{val.id}</Link>
                            {j === client.maaktonderdeeluitvanHuishoudens.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {client.heefttaalniveauTaalniveaus
                      ? client.heefttaalniveauTaalniveaus.map((val, j) => (
                          <span key={j}>
                            <Link to={`/taalniveau/${val.id}`}>{val.id}</Link>
                            {j === client.heefttaalniveauTaalniveaus.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {client.ondersteuntclientClientbegeleiders
                      ? client.ondersteuntclientClientbegeleiders.map((val, j) => (
                          <span key={j}>
                            <Link to={`/clientbegeleider/${val.id}`}>{val.id}</Link>
                            {j === client.ondersteuntclientClientbegeleiders.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/client/${client.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/client/${client.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/client/${client.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Clients found</div>
        )}
      </div>
    </div>
  );
};

export default Client;
