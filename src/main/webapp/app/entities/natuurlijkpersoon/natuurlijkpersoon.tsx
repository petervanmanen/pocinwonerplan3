import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './natuurlijkpersoon.reducer';

export const Natuurlijkpersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const natuurlijkpersoonList = useAppSelector(state => state.natuurlijkpersoon.entities);
  const loading = useAppSelector(state => state.natuurlijkpersoon.loading);

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
      <h2 id="natuurlijkpersoon-heading" data-cy="NatuurlijkpersoonHeading">
        Natuurlijkpersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/natuurlijkpersoon/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Natuurlijkpersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {natuurlijkpersoonList && natuurlijkpersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('empty')}>
                  Empty <FontAwesomeIcon icon={getSortIconByFieldName('empty')} />
                </th>
                <th className="hand" onClick={sort('aanduidingbijzondernederlanderschappersoon')}>
                  Aanduidingbijzondernederlanderschappersoon{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('aanduidingbijzondernederlanderschappersoon')} />
                </th>
                <th className="hand" onClick={sort('aanduidingnaamgebruik')}>
                  Aanduidingnaamgebruik <FontAwesomeIcon icon={getSortIconByFieldName('aanduidingnaamgebruik')} />
                </th>
                <th className="hand" onClick={sort('aanhefaanschrijving')}>
                  Aanhefaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('aanhefaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('academischetitel')}>
                  Academischetitel <FontAwesomeIcon icon={getSortIconByFieldName('academischetitel')} />
                </th>
                <th className="hand" onClick={sort('achternaam')}>
                  Achternaam <FontAwesomeIcon icon={getSortIconByFieldName('achternaam')} />
                </th>
                <th className="hand" onClick={sort('adellijketitelofpredikaat')}>
                  Adellijketitelofpredikaat <FontAwesomeIcon icon={getSortIconByFieldName('adellijketitelofpredikaat')} />
                </th>
                <th className="hand" onClick={sort('anummer')}>
                  Anummer <FontAwesomeIcon icon={getSortIconByFieldName('anummer')} />
                </th>
                <th className="hand" onClick={sort('burgerservicenummer')}>
                  Burgerservicenummer <FontAwesomeIcon icon={getSortIconByFieldName('burgerservicenummer')} />
                </th>
                <th className="hand" onClick={sort('datumgeboorte')}>
                  Datumgeboorte <FontAwesomeIcon icon={getSortIconByFieldName('datumgeboorte')} />
                </th>
                <th className="hand" onClick={sort('datumoverlijden')}>
                  Datumoverlijden <FontAwesomeIcon icon={getSortIconByFieldName('datumoverlijden')} />
                </th>
                <th className="hand" onClick={sort('geboorteland')}>
                  Geboorteland <FontAwesomeIcon icon={getSortIconByFieldName('geboorteland')} />
                </th>
                <th className="hand" onClick={sort('geboorteplaats')}>
                  Geboorteplaats <FontAwesomeIcon icon={getSortIconByFieldName('geboorteplaats')} />
                </th>
                <th className="hand" onClick={sort('geslachtsaanduiding')}>
                  Geslachtsaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsaanduiding')} />
                </th>
                <th className="hand" onClick={sort('geslachtsnaam')}>
                  Geslachtsnaam <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsnaam')} />
                </th>
                <th className="hand" onClick={sort('geslachtsnaamaanschrijving')}>
                  Geslachtsnaamaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('geslachtsnaamaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('handlichting')}>
                  Handlichting <FontAwesomeIcon icon={getSortIconByFieldName('handlichting')} />
                </th>
                <th className="hand" onClick={sort('indicatieafschermingpersoonsgegevens')}>
                  Indicatieafschermingpersoonsgegevens{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('indicatieafschermingpersoonsgegevens')} />
                </th>
                <th className="hand" onClick={sort('indicatieoverleden')}>
                  Indicatieoverleden <FontAwesomeIcon icon={getSortIconByFieldName('indicatieoverleden')} />
                </th>
                <th className="hand" onClick={sort('landoverlijden')}>
                  Landoverlijden <FontAwesomeIcon icon={getSortIconByFieldName('landoverlijden')} />
                </th>
                <th className="hand" onClick={sort('nationaliteit')}>
                  Nationaliteit <FontAwesomeIcon icon={getSortIconByFieldName('nationaliteit')} />
                </th>
                <th className="hand" onClick={sort('overlijdensplaats')}>
                  Overlijdensplaats <FontAwesomeIcon icon={getSortIconByFieldName('overlijdensplaats')} />
                </th>
                <th className="hand" onClick={sort('voorlettersaanschrijving')}>
                  Voorlettersaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('voorlettersaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('voornamen')}>
                  Voornamen <FontAwesomeIcon icon={getSortIconByFieldName('voornamen')} />
                </th>
                <th className="hand" onClick={sort('voornamenaanschrijving')}>
                  Voornamenaanschrijving <FontAwesomeIcon icon={getSortIconByFieldName('voornamenaanschrijving')} />
                </th>
                <th className="hand" onClick={sort('voorvoegselgeslachtsnaam')}>
                  Voorvoegselgeslachtsnaam <FontAwesomeIcon icon={getSortIconByFieldName('voorvoegselgeslachtsnaam')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {natuurlijkpersoonList.map((natuurlijkpersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/natuurlijkpersoon/${natuurlijkpersoon.id}`} color="link" size="sm">
                      {natuurlijkpersoon.id}
                    </Button>
                  </td>
                  <td>{natuurlijkpersoon.empty}</td>
                  <td>{natuurlijkpersoon.aanduidingbijzondernederlanderschappersoon}</td>
                  <td>{natuurlijkpersoon.aanduidingnaamgebruik}</td>
                  <td>{natuurlijkpersoon.aanhefaanschrijving}</td>
                  <td>{natuurlijkpersoon.academischetitel}</td>
                  <td>{natuurlijkpersoon.achternaam}</td>
                  <td>{natuurlijkpersoon.adellijketitelofpredikaat}</td>
                  <td>{natuurlijkpersoon.anummer}</td>
                  <td>{natuurlijkpersoon.burgerservicenummer}</td>
                  <td>{natuurlijkpersoon.datumgeboorte}</td>
                  <td>{natuurlijkpersoon.datumoverlijden}</td>
                  <td>{natuurlijkpersoon.geboorteland}</td>
                  <td>{natuurlijkpersoon.geboorteplaats}</td>
                  <td>{natuurlijkpersoon.geslachtsaanduiding}</td>
                  <td>{natuurlijkpersoon.geslachtsnaam}</td>
                  <td>{natuurlijkpersoon.geslachtsnaamaanschrijving}</td>
                  <td>{natuurlijkpersoon.handlichting}</td>
                  <td>{natuurlijkpersoon.indicatieafschermingpersoonsgegevens ? 'true' : 'false'}</td>
                  <td>{natuurlijkpersoon.indicatieoverleden ? 'true' : 'false'}</td>
                  <td>{natuurlijkpersoon.landoverlijden}</td>
                  <td>{natuurlijkpersoon.nationaliteit}</td>
                  <td>{natuurlijkpersoon.overlijdensplaats}</td>
                  <td>{natuurlijkpersoon.voorlettersaanschrijving}</td>
                  <td>{natuurlijkpersoon.voornamen}</td>
                  <td>{natuurlijkpersoon.voornamenaanschrijving}</td>
                  <td>{natuurlijkpersoon.voorvoegselgeslachtsnaam}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/natuurlijkpersoon/${natuurlijkpersoon.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/natuurlijkpersoon/${natuurlijkpersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/natuurlijkpersoon/${natuurlijkpersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Natuurlijkpersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Natuurlijkpersoon;
