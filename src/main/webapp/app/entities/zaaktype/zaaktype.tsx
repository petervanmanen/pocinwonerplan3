import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './zaaktype.reducer';

export const Zaaktype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const zaaktypeList = useAppSelector(state => state.zaaktype.entities);
  const loading = useAppSelector(state => state.zaaktype.loading);

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
      <h2 id="zaaktype-heading" data-cy="ZaaktypeHeading">
        Zaaktypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/zaaktype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Zaaktype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zaaktypeList && zaaktypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('archiefcode')}>
                  Archiefcode <FontAwesomeIcon icon={getSortIconByFieldName('archiefcode')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidzaaktype')}>
                  Datumbegingeldigheidzaaktype <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidzaaktype')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidzaaktype')}>
                  Datumeindegeldigheidzaaktype <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidzaaktype')} />
                </th>
                <th className="hand" onClick={sort('doorlooptijdbehandeling')}>
                  Doorlooptijdbehandeling <FontAwesomeIcon icon={getSortIconByFieldName('doorlooptijdbehandeling')} />
                </th>
                <th className="hand" onClick={sort('indicatiepublicatie')}>
                  Indicatiepublicatie <FontAwesomeIcon icon={getSortIconByFieldName('indicatiepublicatie')} />
                </th>
                <th className="hand" onClick={sort('publicatietekst')}>
                  Publicatietekst <FontAwesomeIcon icon={getSortIconByFieldName('publicatietekst')} />
                </th>
                <th className="hand" onClick={sort('servicenormbehandeling')}>
                  Servicenormbehandeling <FontAwesomeIcon icon={getSortIconByFieldName('servicenormbehandeling')} />
                </th>
                <th className="hand" onClick={sort('trefwoord')}>
                  Trefwoord <FontAwesomeIcon icon={getSortIconByFieldName('trefwoord')} />
                </th>
                <th className="hand" onClick={sort('vertrouwelijkaanduiding')}>
                  Vertrouwelijkaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('vertrouwelijkaanduiding')} />
                </th>
                <th className="hand" onClick={sort('zaakcategorie')}>
                  Zaakcategorie <FontAwesomeIcon icon={getSortIconByFieldName('zaakcategorie')} />
                </th>
                <th className="hand" onClick={sort('zaaktypeomschrijving')}>
                  Zaaktypeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('zaaktypeomschrijving')} />
                </th>
                <th className="hand" onClick={sort('zaaktypeomschrijvinggeneriek')}>
                  Zaaktypeomschrijvinggeneriek <FontAwesomeIcon icon={getSortIconByFieldName('zaaktypeomschrijvinggeneriek')} />
                </th>
                <th>
                  Heeft Producttype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Product <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Bedrijfsprocestype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isverantwoordelijkevoor Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isaanleidingvoor Formuliersoort <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zaaktypeList.map((zaaktype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/zaaktype/${zaaktype.id}`} color="link" size="sm">
                      {zaaktype.id}
                    </Button>
                  </td>
                  <td>{zaaktype.archiefcode}</td>
                  <td>{zaaktype.datumbegingeldigheidzaaktype}</td>
                  <td>{zaaktype.datumeindegeldigheidzaaktype}</td>
                  <td>{zaaktype.doorlooptijdbehandeling}</td>
                  <td>{zaaktype.indicatiepublicatie}</td>
                  <td>{zaaktype.publicatietekst}</td>
                  <td>{zaaktype.servicenormbehandeling}</td>
                  <td>{zaaktype.trefwoord}</td>
                  <td>{zaaktype.vertrouwelijkaanduiding}</td>
                  <td>{zaaktype.zaakcategorie}</td>
                  <td>{zaaktype.zaaktypeomschrijving}</td>
                  <td>{zaaktype.zaaktypeomschrijvinggeneriek}</td>
                  <td>
                    {zaaktype.heeftProducttype ? (
                      <Link to={`/producttype/${zaaktype.heeftProducttype.id}`}>{zaaktype.heeftProducttype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zaaktype.betreftProduct ? <Link to={`/product/${zaaktype.betreftProduct.id}`}>{zaaktype.betreftProduct.id}</Link> : ''}
                  </td>
                  <td>
                    {zaaktype.heeftBedrijfsprocestype ? (
                      <Link to={`/bedrijfsprocestype/${zaaktype.heeftBedrijfsprocestype.id}`}>{zaaktype.heeftBedrijfsprocestype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zaaktype.isverantwoordelijkevoorMedewerker ? (
                      <Link to={`/medewerker/${zaaktype.isverantwoordelijkevoorMedewerker.id}`}>
                        {zaaktype.isverantwoordelijkevoorMedewerker.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zaaktype.isaanleidingvoorFormuliersoorts
                      ? zaaktype.isaanleidingvoorFormuliersoorts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/formuliersoort/${val.id}`}>{val.id}</Link>
                            {j === zaaktype.isaanleidingvoorFormuliersoorts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/zaaktype/${zaaktype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/zaaktype/${zaaktype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/zaaktype/${zaaktype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Zaaktypes found</div>
        )}
      </div>
    </div>
  );
};

export default Zaaktype;
