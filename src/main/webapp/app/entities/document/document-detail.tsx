import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document.reducer';

export const DocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">Document</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="cocumentbeschrijving">Cocumentbeschrijving</span>
          </dt>
          <dd>{documentEntity.cocumentbeschrijving}</dd>
          <dt>
            <span id="datumcreatiedocument">Datumcreatiedocument</span>
          </dt>
          <dd>{documentEntity.datumcreatiedocument}</dd>
          <dt>
            <span id="datumontvangstdocument">Datumontvangstdocument</span>
          </dt>
          <dd>{documentEntity.datumontvangstdocument}</dd>
          <dt>
            <span id="datumverzendingdocument">Datumverzendingdocument</span>
          </dt>
          <dd>{documentEntity.datumverzendingdocument}</dd>
          <dt>
            <span id="documentauteur">Documentauteur</span>
          </dt>
          <dd>{documentEntity.documentauteur}</dd>
          <dt>
            <span id="documentidentificatie">Documentidentificatie</span>
          </dt>
          <dd>{documentEntity.documentidentificatie}</dd>
          <dt>
            <span id="documenttitel">Documenttitel</span>
          </dt>
          <dd>{documentEntity.documenttitel}</dd>
          <dt>
            <span id="vertrouwelijkaanduiding">Vertrouwelijkaanduiding</span>
          </dt>
          <dd>{documentEntity.vertrouwelijkaanduiding}</dd>
          <dt>Heeftkenmerk Identificatiekenmerk</dt>
          <dd>{documentEntity.heeftkenmerkIdentificatiekenmerk ? documentEntity.heeftkenmerkIdentificatiekenmerk.id : ''}</dd>
          <dt>Isvan Documenttype</dt>
          <dd>{documentEntity.isvanDocumenttype ? documentEntity.isvanDocumenttype.id : ''}</dd>
          <dt>Inspectierapport Binnenlocatie</dt>
          <dd>{documentEntity.inspectierapportBinnenlocatie ? documentEntity.inspectierapportBinnenlocatie.id : ''}</dd>
          <dt>Heeft Rapportagemoment</dt>
          <dd>{documentEntity.heeftRapportagemoment ? documentEntity.heeftRapportagemoment.id : ''}</dd>
          <dt>Heeftdocumenten Applicatie</dt>
          <dd>
            {documentEntity.heeftdocumentenApplicaties
              ? documentEntity.heeftdocumentenApplicaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {documentEntity.heeftdocumentenApplicaties && i === documentEntity.heeftdocumentenApplicaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Kanvastgelegdzijnals Besluit</dt>
          <dd>
            {documentEntity.kanvastgelegdzijnalsBesluits
              ? documentEntity.kanvastgelegdzijnalsBesluits.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {documentEntity.kanvastgelegdzijnalsBesluits && i === documentEntity.kanvastgelegdzijnalsBesluits.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Kent Zaak</dt>
          <dd>
            {documentEntity.kentZaaks
              ? documentEntity.kentZaaks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {documentEntity.kentZaaks && i === documentEntity.kentZaaks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
