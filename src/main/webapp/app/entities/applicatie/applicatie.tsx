import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './applicatie.reducer';

export const Applicatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const applicatieList = useAppSelector(state => state.applicatie.entities);
  const loading = useAppSelector(state => state.applicatie.loading);

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
      <h2 id="applicatie-heading" data-cy="ApplicatieHeading">
        Applicaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/applicatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Applicatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {applicatieList && applicatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('applicatieurl')}>
                  Applicatieurl <FontAwesomeIcon icon={getSortIconByFieldName('applicatieurl')} />
                </th>
                <th className="hand" onClick={sort('beheerstatus')}>
                  Beheerstatus <FontAwesomeIcon icon={getSortIconByFieldName('beheerstatus')} />
                </th>
                <th className="hand" onClick={sort('beleidsdomein')}>
                  Beleidsdomein <FontAwesomeIcon icon={getSortIconByFieldName('beleidsdomein')} />
                </th>
                <th className="hand" onClick={sort('categorie')}>
                  Categorie <FontAwesomeIcon icon={getSortIconByFieldName('categorie')} />
                </th>
                <th className="hand" onClick={sort('guid')}>
                  Guid <FontAwesomeIcon icon={getSortIconByFieldName('guid')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('packagingstatus')}>
                  Packagingstatus <FontAwesomeIcon icon={getSortIconByFieldName('packagingstatus')} />
                </th>
                <th>
                  Heeftleverancier Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftdocumenten Document <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Rollen Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {applicatieList.map((applicatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/applicatie/${applicatie.id}`} color="link" size="sm">
                      {applicatie.id}
                    </Button>
                  </td>
                  <td>{applicatie.applicatieurl}</td>
                  <td>{applicatie.beheerstatus}</td>
                  <td>{applicatie.beleidsdomein}</td>
                  <td>{applicatie.categorie}</td>
                  <td>{applicatie.guid}</td>
                  <td>{applicatie.naam}</td>
                  <td>{applicatie.omschrijving}</td>
                  <td>{applicatie.packagingstatus}</td>
                  <td>
                    {applicatie.heeftleverancierLeverancier ? (
                      <Link to={`/leverancier/${applicatie.heeftleverancierLeverancier.id}`}>
                        {applicatie.heeftleverancierLeverancier.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {applicatie.heeftdocumentenDocuments
                      ? applicatie.heeftdocumentenDocuments.map((val, j) => (
                          <span key={j}>
                            <Link to={`/document/${val.id}`}>{val.id}</Link>
                            {j === applicatie.heeftdocumentenDocuments.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {applicatie.rollenMedewerkers
                      ? applicatie.rollenMedewerkers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/medewerker/${val.id}`}>{val.id}</Link>
                            {j === applicatie.rollenMedewerkers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/applicatie/${applicatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/applicatie/${applicatie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/applicatie/${applicatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Applicaties found</div>
        )}
      </div>
    </div>
  );
};

export default Applicatie;
