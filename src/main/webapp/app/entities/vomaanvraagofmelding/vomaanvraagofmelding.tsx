import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vomaanvraagofmelding.reducer';

export const Vomaanvraagofmelding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vomaanvraagofmeldingList = useAppSelector(state => state.vomaanvraagofmelding.entities);
  const loading = useAppSelector(state => state.vomaanvraagofmelding.loading);

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
      <h2 id="vomaanvraagofmelding-heading" data-cy="VomaanvraagofmeldingHeading">
        Vomaanvraagofmeldings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/vomaanvraagofmelding/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vomaanvraagofmelding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vomaanvraagofmeldingList && vomaanvraagofmeldingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('activiteiten')}>
                  Activiteiten <FontAwesomeIcon icon={getSortIconByFieldName('activiteiten')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th className="hand" onClick={sort('bagid')}>
                  Bagid <FontAwesomeIcon icon={getSortIconByFieldName('bagid')} />
                </th>
                <th className="hand" onClick={sort('dossiernummer')}>
                  Dossiernummer <FontAwesomeIcon icon={getSortIconByFieldName('dossiernummer')} />
                </th>
                <th className="hand" onClick={sort('intaketype')}>
                  Intaketype <FontAwesomeIcon icon={getSortIconByFieldName('intaketype')} />
                </th>
                <th className="hand" onClick={sort('internnummer')}>
                  Internnummer <FontAwesomeIcon icon={getSortIconByFieldName('internnummer')} />
                </th>
                <th className="hand" onClick={sort('kadastraleaanduiding')}>
                  Kadastraleaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('kadastraleaanduiding')} />
                </th>
                <th className="hand" onClick={sort('kenmerk')}>
                  Kenmerk <FontAwesomeIcon icon={getSortIconByFieldName('kenmerk')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('locatieomschrijving')}>
                  Locatieomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('locatieomschrijving')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vomaanvraagofmeldingList.map((vomaanvraagofmelding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vomaanvraagofmelding/${vomaanvraagofmelding.id}`} color="link" size="sm">
                      {vomaanvraagofmelding.id}
                    </Button>
                  </td>
                  <td>{vomaanvraagofmelding.activiteiten}</td>
                  <td>{vomaanvraagofmelding.adres}</td>
                  <td>{vomaanvraagofmelding.bagid}</td>
                  <td>{vomaanvraagofmelding.dossiernummer}</td>
                  <td>{vomaanvraagofmelding.intaketype}</td>
                  <td>{vomaanvraagofmelding.internnummer}</td>
                  <td>{vomaanvraagofmelding.kadastraleaanduiding}</td>
                  <td>{vomaanvraagofmelding.kenmerk}</td>
                  <td>{vomaanvraagofmelding.locatie}</td>
                  <td>{vomaanvraagofmelding.locatieomschrijving}</td>
                  <td>{vomaanvraagofmelding.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/vomaanvraagofmelding/${vomaanvraagofmelding.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vomaanvraagofmelding/${vomaanvraagofmelding.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vomaanvraagofmelding/${vomaanvraagofmelding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vomaanvraagofmeldings found</div>
        )}
      </div>
    </div>
  );
};

export default Vomaanvraagofmelding;
