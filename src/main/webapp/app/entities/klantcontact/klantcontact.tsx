import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './klantcontact.reducer';

export const Klantcontact = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const klantcontactList = useAppSelector(state => state.klantcontact.entities);
  const loading = useAppSelector(state => state.klantcontact.loading);

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
      <h2 id="klantcontact-heading" data-cy="KlantcontactHeading">
        Klantcontacts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/klantcontact/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Klantcontact
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {klantcontactList && klantcontactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('eindtijd')}>
                  Eindtijd <FontAwesomeIcon icon={getSortIconByFieldName('eindtijd')} />
                </th>
                <th className="hand" onClick={sort('kanaal')}>
                  Kanaal <FontAwesomeIcon icon={getSortIconByFieldName('kanaal')} />
                </th>
                <th className="hand" onClick={sort('notitie')}>
                  Notitie <FontAwesomeIcon icon={getSortIconByFieldName('notitie')} />
                </th>
                <th className="hand" onClick={sort('starttijd')}>
                  Starttijd <FontAwesomeIcon icon={getSortIconByFieldName('starttijd')} />
                </th>
                <th className="hand" onClick={sort('tijdsduur')}>
                  Tijdsduur <FontAwesomeIcon icon={getSortIconByFieldName('tijdsduur')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('wachttijdtotaal')}>
                  Wachttijdtotaal <FontAwesomeIcon icon={getSortIconByFieldName('wachttijdtotaal')} />
                </th>
                <th>
                  Heeftklantcontacten Betrokkene <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftbetrekkingop Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isgevoerddoor Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Telefoononderwerp <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Mondtuitin Telefoontje <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {klantcontactList.map((klantcontact, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/klantcontact/${klantcontact.id}`} color="link" size="sm">
                      {klantcontact.id}
                    </Button>
                  </td>
                  <td>{klantcontact.eindtijd}</td>
                  <td>{klantcontact.kanaal}</td>
                  <td>{klantcontact.notitie}</td>
                  <td>{klantcontact.starttijd}</td>
                  <td>{klantcontact.tijdsduur}</td>
                  <td>{klantcontact.toelichting}</td>
                  <td>{klantcontact.wachttijdtotaal}</td>
                  <td>
                    {klantcontact.heeftklantcontactenBetrokkene ? (
                      <Link to={`/betrokkene/${klantcontact.heeftklantcontactenBetrokkene.id}`}>
                        {klantcontact.heeftklantcontactenBetrokkene.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {klantcontact.heeftbetrekkingopZaak ? (
                      <Link to={`/zaak/${klantcontact.heeftbetrekkingopZaak.id}`}>{klantcontact.heeftbetrekkingopZaak.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {klantcontact.isgevoerddoorMedewerker ? (
                      <Link to={`/medewerker/${klantcontact.isgevoerddoorMedewerker.id}`}>{klantcontact.isgevoerddoorMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {klantcontact.heeftTelefoononderwerp ? (
                      <Link to={`/telefoononderwerp/${klantcontact.heeftTelefoononderwerp.id}`}>
                        {klantcontact.heeftTelefoononderwerp.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {klantcontact.mondtuitinTelefoontje ? (
                      <Link to={`/telefoontje/${klantcontact.mondtuitinTelefoontje.id}`}>{klantcontact.mondtuitinTelefoontje.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/klantcontact/${klantcontact.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/klantcontact/${klantcontact.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/klantcontact/${klantcontact.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Klantcontacts found</div>
        )}
      </div>
    </div>
  );
};

export default Klantcontact;
