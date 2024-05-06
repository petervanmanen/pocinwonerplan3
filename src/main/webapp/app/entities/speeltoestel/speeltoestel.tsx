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

import { getEntities } from './speeltoestel.reducer';

export const Speeltoestel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const speeltoestelList = useAppSelector(state => state.speeltoestel.entities);
  const loading = useAppSelector(state => state.speeltoestel.loading);

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
      <h2 id="speeltoestel-heading" data-cy="SpeeltoestelHeading">
        Speeltoestels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/speeltoestel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Speeltoestel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {speeltoestelList && speeltoestelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('catalogusprijs')}>
                  Catalogusprijs <FontAwesomeIcon icon={getSortIconByFieldName('catalogusprijs')} />
                </th>
                <th className="hand" onClick={sort('certificaat')}>
                  Certificaat <FontAwesomeIcon icon={getSortIconByFieldName('certificaat')} />
                </th>
                <th className="hand" onClick={sort('certificaatnummer')}>
                  Certificaatnummer <FontAwesomeIcon icon={getSortIconByFieldName('certificaatnummer')} />
                </th>
                <th className="hand" onClick={sort('certificeringsinstantie')}>
                  Certificeringsinstantie <FontAwesomeIcon icon={getSortIconByFieldName('certificeringsinstantie')} />
                </th>
                <th className="hand" onClick={sort('controlefrequentie')}>
                  Controlefrequentie <FontAwesomeIcon icon={getSortIconByFieldName('controlefrequentie')} />
                </th>
                <th className="hand" onClick={sort('datumcertificaat')}>
                  Datumcertificaat <FontAwesomeIcon icon={getSortIconByFieldName('datumcertificaat')} />
                </th>
                <th className="hand" onClick={sort('gemakkelijktoegankelijk')}>
                  Gemakkelijktoegankelijk <FontAwesomeIcon icon={getSortIconByFieldName('gemakkelijktoegankelijk')} />
                </th>
                <th className="hand" onClick={sort('inspectievolgorde')}>
                  Inspectievolgorde <FontAwesomeIcon icon={getSortIconByFieldName('inspectievolgorde')} />
                </th>
                <th className="hand" onClick={sort('installatiekosten')}>
                  Installatiekosten <FontAwesomeIcon icon={getSortIconByFieldName('installatiekosten')} />
                </th>
                <th className="hand" onClick={sort('speelterrein')}>
                  Speelterrein <FontAwesomeIcon icon={getSortIconByFieldName('speelterrein')} />
                </th>
                <th className="hand" onClick={sort('speeltoesteltoestelonderdeel')}>
                  Speeltoesteltoestelonderdeel <FontAwesomeIcon icon={getSortIconByFieldName('speeltoesteltoestelonderdeel')} />
                </th>
                <th className="hand" onClick={sort('technischelevensduur')}>
                  Technischelevensduur <FontAwesomeIcon icon={getSortIconByFieldName('technischelevensduur')} />
                </th>
                <th className="hand" onClick={sort('toestelcode')}>
                  Toestelcode <FontAwesomeIcon icon={getSortIconByFieldName('toestelcode')} />
                </th>
                <th className="hand" onClick={sort('toestelgroep')}>
                  Toestelgroep <FontAwesomeIcon icon={getSortIconByFieldName('toestelgroep')} />
                </th>
                <th className="hand" onClick={sort('toestelnaam')}>
                  Toestelnaam <FontAwesomeIcon icon={getSortIconByFieldName('toestelnaam')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typenummer')}>
                  Typenummer <FontAwesomeIcon icon={getSortIconByFieldName('typenummer')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typeplus2')}>
                  Typeplus 2 <FontAwesomeIcon icon={getSortIconByFieldName('typeplus2')} />
                </th>
                <th className="hand" onClick={sort('valruimtehoogte')}>
                  Valruimtehoogte <FontAwesomeIcon icon={getSortIconByFieldName('valruimtehoogte')} />
                </th>
                <th className="hand" onClick={sort('valruimteomvang')}>
                  Valruimteomvang <FontAwesomeIcon icon={getSortIconByFieldName('valruimteomvang')} />
                </th>
                <th className="hand" onClick={sort('vrijevalhoogte')}>
                  Vrijevalhoogte <FontAwesomeIcon icon={getSortIconByFieldName('vrijevalhoogte')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {speeltoestelList.map((speeltoestel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/speeltoestel/${speeltoestel.id}`} color="link" size="sm">
                      {speeltoestel.id}
                    </Button>
                  </td>
                  <td>{speeltoestel.catalogusprijs}</td>
                  <td>{speeltoestel.certificaat ? 'true' : 'false'}</td>
                  <td>{speeltoestel.certificaatnummer}</td>
                  <td>{speeltoestel.certificeringsinstantie}</td>
                  <td>{speeltoestel.controlefrequentie}</td>
                  <td>
                    {speeltoestel.datumcertificaat ? (
                      <TextFormat type="date" value={speeltoestel.datumcertificaat} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{speeltoestel.gemakkelijktoegankelijk ? 'true' : 'false'}</td>
                  <td>{speeltoestel.inspectievolgorde}</td>
                  <td>{speeltoestel.installatiekosten}</td>
                  <td>{speeltoestel.speelterrein}</td>
                  <td>{speeltoestel.speeltoesteltoestelonderdeel}</td>
                  <td>{speeltoestel.technischelevensduur}</td>
                  <td>{speeltoestel.toestelcode}</td>
                  <td>{speeltoestel.toestelgroep}</td>
                  <td>{speeltoestel.toestelnaam}</td>
                  <td>{speeltoestel.type}</td>
                  <td>{speeltoestel.typenummer}</td>
                  <td>{speeltoestel.typeplus}</td>
                  <td>{speeltoestel.typeplus2}</td>
                  <td>{speeltoestel.valruimtehoogte}</td>
                  <td>{speeltoestel.valruimteomvang}</td>
                  <td>{speeltoestel.vrijevalhoogte}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/speeltoestel/${speeltoestel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/speeltoestel/${speeltoestel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/speeltoestel/${speeltoestel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Speeltoestels found</div>
        )}
      </div>
    </div>
  );
};

export default Speeltoestel;
