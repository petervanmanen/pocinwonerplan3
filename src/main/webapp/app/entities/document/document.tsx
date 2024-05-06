import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './document.reducer';

export const Document = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const documentList = useAppSelector(state => state.document.entities);
  const loading = useAppSelector(state => state.document.loading);

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
      <h2 id="document-heading" data-cy="DocumentHeading">
        Documents
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/document/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Document
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {documentList && documentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('cocumentbeschrijving')}>
                  Cocumentbeschrijving <FontAwesomeIcon icon={getSortIconByFieldName('cocumentbeschrijving')} />
                </th>
                <th className="hand" onClick={sort('datumcreatiedocument')}>
                  Datumcreatiedocument <FontAwesomeIcon icon={getSortIconByFieldName('datumcreatiedocument')} />
                </th>
                <th className="hand" onClick={sort('datumontvangstdocument')}>
                  Datumontvangstdocument <FontAwesomeIcon icon={getSortIconByFieldName('datumontvangstdocument')} />
                </th>
                <th className="hand" onClick={sort('datumverzendingdocument')}>
                  Datumverzendingdocument <FontAwesomeIcon icon={getSortIconByFieldName('datumverzendingdocument')} />
                </th>
                <th className="hand" onClick={sort('documentauteur')}>
                  Documentauteur <FontAwesomeIcon icon={getSortIconByFieldName('documentauteur')} />
                </th>
                <th className="hand" onClick={sort('documentidentificatie')}>
                  Documentidentificatie <FontAwesomeIcon icon={getSortIconByFieldName('documentidentificatie')} />
                </th>
                <th className="hand" onClick={sort('documenttitel')}>
                  Documenttitel <FontAwesomeIcon icon={getSortIconByFieldName('documenttitel')} />
                </th>
                <th className="hand" onClick={sort('vertrouwelijkaanduiding')}>
                  Vertrouwelijkaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('vertrouwelijkaanduiding')} />
                </th>
                <th>
                  Heeftkenmerk Identificatiekenmerk <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isvan Documenttype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Inspectierapport Binnenlocatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Rapportagemoment <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftdocumenten Applicatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Kanvastgelegdzijnals Besluit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Kent Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {documentList.map((document, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/document/${document.id}`} color="link" size="sm">
                      {document.id}
                    </Button>
                  </td>
                  <td>{document.cocumentbeschrijving}</td>
                  <td>{document.datumcreatiedocument}</td>
                  <td>{document.datumontvangstdocument}</td>
                  <td>{document.datumverzendingdocument}</td>
                  <td>{document.documentauteur}</td>
                  <td>{document.documentidentificatie}</td>
                  <td>{document.documenttitel}</td>
                  <td>{document.vertrouwelijkaanduiding}</td>
                  <td>
                    {document.heeftkenmerkIdentificatiekenmerk ? (
                      <Link to={`/identificatiekenmerk/${document.heeftkenmerkIdentificatiekenmerk.id}`}>
                        {document.heeftkenmerkIdentificatiekenmerk.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {document.isvanDocumenttype ? (
                      <Link to={`/documenttype/${document.isvanDocumenttype.id}`}>{document.isvanDocumenttype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {document.inspectierapportBinnenlocatie ? (
                      <Link to={`/binnenlocatie/${document.inspectierapportBinnenlocatie.id}`}>
                        {document.inspectierapportBinnenlocatie.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {document.heeftRapportagemoment ? (
                      <Link to={`/rapportagemoment/${document.heeftRapportagemoment.id}`}>{document.heeftRapportagemoment.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {document.heeftdocumentenApplicaties
                      ? document.heeftdocumentenApplicaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/applicatie/${val.id}`}>{val.id}</Link>
                            {j === document.heeftdocumentenApplicaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {document.kanvastgelegdzijnalsBesluits
                      ? document.kanvastgelegdzijnalsBesluits.map((val, j) => (
                          <span key={j}>
                            <Link to={`/besluit/${val.id}`}>{val.id}</Link>
                            {j === document.kanvastgelegdzijnalsBesluits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {document.kentZaaks
                      ? document.kentZaaks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/zaak/${val.id}`}>{val.id}</Link>
                            {j === document.kentZaaks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/document/${document.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/document/${document.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/document/${document.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Documents found</div>
        )}
      </div>
    </div>
  );
};

export default Document;
