import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IIdentificatiekenmerk } from 'app/shared/model/identificatiekenmerk.model';
import { getEntities as getIdentificatiekenmerks } from 'app/entities/identificatiekenmerk/identificatiekenmerk.reducer';
import { IDocumenttype } from 'app/shared/model/documenttype.model';
import { getEntities as getDocumenttypes } from 'app/entities/documenttype/documenttype.reducer';
import { IBinnenlocatie } from 'app/shared/model/binnenlocatie.model';
import { getEntities as getBinnenlocaties } from 'app/entities/binnenlocatie/binnenlocatie.reducer';
import { IRapportagemoment } from 'app/shared/model/rapportagemoment.model';
import { getEntities as getRapportagemoments } from 'app/entities/rapportagemoment/rapportagemoment.reducer';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntities as getApplicaties } from 'app/entities/applicatie/applicatie.reducer';
import { IBesluit } from 'app/shared/model/besluit.model';
import { getEntities as getBesluits } from 'app/entities/besluit/besluit.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntity, updateEntity, createEntity, reset } from './document.reducer';

export const DocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const identificatiekenmerks = useAppSelector(state => state.identificatiekenmerk.entities);
  const documenttypes = useAppSelector(state => state.documenttype.entities);
  const binnenlocaties = useAppSelector(state => state.binnenlocatie.entities);
  const rapportagemoments = useAppSelector(state => state.rapportagemoment.entities);
  const applicaties = useAppSelector(state => state.applicatie.entities);
  const besluits = useAppSelector(state => state.besluit.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const documentEntity = useAppSelector(state => state.document.entity);
  const loading = useAppSelector(state => state.document.loading);
  const updating = useAppSelector(state => state.document.updating);
  const updateSuccess = useAppSelector(state => state.document.updateSuccess);

  const handleClose = () => {
    navigate('/document');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getIdentificatiekenmerks({}));
    dispatch(getDocumenttypes({}));
    dispatch(getBinnenlocaties({}));
    dispatch(getRapportagemoments({}));
    dispatch(getApplicaties({}));
    dispatch(getBesluits({}));
    dispatch(getZaaks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...documentEntity,
      ...values,
      heeftkenmerkIdentificatiekenmerk: identificatiekenmerks.find(
        it => it.id.toString() === values.heeftkenmerkIdentificatiekenmerk?.toString(),
      ),
      isvanDocumenttype: documenttypes.find(it => it.id.toString() === values.isvanDocumenttype?.toString()),
      inspectierapportBinnenlocatie: binnenlocaties.find(it => it.id.toString() === values.inspectierapportBinnenlocatie?.toString()),
      heeftRapportagemoment: rapportagemoments.find(it => it.id.toString() === values.heeftRapportagemoment?.toString()),
      heeftdocumentenApplicaties: mapIdList(values.heeftdocumentenApplicaties),
      kanvastgelegdzijnalsBesluits: mapIdList(values.kanvastgelegdzijnalsBesluits),
      kentZaaks: mapIdList(values.kentZaaks),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...documentEntity,
          heeftkenmerkIdentificatiekenmerk: documentEntity?.heeftkenmerkIdentificatiekenmerk?.id,
          isvanDocumenttype: documentEntity?.isvanDocumenttype?.id,
          inspectierapportBinnenlocatie: documentEntity?.inspectierapportBinnenlocatie?.id,
          heeftRapportagemoment: documentEntity?.heeftRapportagemoment?.id,
          heeftdocumentenApplicaties: documentEntity?.heeftdocumentenApplicaties?.map(e => e.id.toString()),
          kanvastgelegdzijnalsBesluits: documentEntity?.kanvastgelegdzijnalsBesluits?.map(e => e.id.toString()),
          kentZaaks: documentEntity?.kentZaaks?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.document.home.createOrEditLabel" data-cy="DocumentCreateUpdateHeading">
            Create or edit a Document
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="document-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Cocumentbeschrijving"
                id="document-cocumentbeschrijving"
                name="cocumentbeschrijving"
                data-cy="cocumentbeschrijving"
                type="text"
              />
              <ValidatedField
                label="Datumcreatiedocument"
                id="document-datumcreatiedocument"
                name="datumcreatiedocument"
                data-cy="datumcreatiedocument"
                type="text"
              />
              <ValidatedField
                label="Datumontvangstdocument"
                id="document-datumontvangstdocument"
                name="datumontvangstdocument"
                data-cy="datumontvangstdocument"
                type="text"
              />
              <ValidatedField
                label="Datumverzendingdocument"
                id="document-datumverzendingdocument"
                name="datumverzendingdocument"
                data-cy="datumverzendingdocument"
                type="text"
              />
              <ValidatedField
                label="Documentauteur"
                id="document-documentauteur"
                name="documentauteur"
                data-cy="documentauteur"
                type="text"
              />
              <ValidatedField
                label="Documentidentificatie"
                id="document-documentidentificatie"
                name="documentidentificatie"
                data-cy="documentidentificatie"
                type="text"
              />
              <ValidatedField label="Documenttitel" id="document-documenttitel" name="documenttitel" data-cy="documenttitel" type="text" />
              <ValidatedField
                label="Vertrouwelijkaanduiding"
                id="document-vertrouwelijkaanduiding"
                name="vertrouwelijkaanduiding"
                data-cy="vertrouwelijkaanduiding"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                id="document-heeftkenmerkIdentificatiekenmerk"
                name="heeftkenmerkIdentificatiekenmerk"
                data-cy="heeftkenmerkIdentificatiekenmerk"
                label="Heeftkenmerk Identificatiekenmerk"
                type="select"
              >
                <option value="" key="0" />
                {identificatiekenmerks
                  ? identificatiekenmerks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-isvanDocumenttype"
                name="isvanDocumenttype"
                data-cy="isvanDocumenttype"
                label="Isvan Documenttype"
                type="select"
              >
                <option value="" key="0" />
                {documenttypes
                  ? documenttypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-inspectierapportBinnenlocatie"
                name="inspectierapportBinnenlocatie"
                data-cy="inspectierapportBinnenlocatie"
                label="Inspectierapport Binnenlocatie"
                type="select"
              >
                <option value="" key="0" />
                {binnenlocaties
                  ? binnenlocaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="document-heeftRapportagemoment"
                name="heeftRapportagemoment"
                data-cy="heeftRapportagemoment"
                label="Heeft Rapportagemoment"
                type="select"
              >
                <option value="" key="0" />
                {rapportagemoments
                  ? rapportagemoments.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeftdocumenten Applicatie"
                id="document-heeftdocumentenApplicatie"
                data-cy="heeftdocumentenApplicatie"
                type="select"
                multiple
                name="heeftdocumentenApplicaties"
              >
                <option value="" key="0" />
                {applicaties
                  ? applicaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Kanvastgelegdzijnals Besluit"
                id="document-kanvastgelegdzijnalsBesluit"
                data-cy="kanvastgelegdzijnalsBesluit"
                type="select"
                multiple
                name="kanvastgelegdzijnalsBesluits"
              >
                <option value="" key="0" />
                {besluits
                  ? besluits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField label="Kent Zaak" id="document-kentZaak" data-cy="kentZaak" type="select" multiple name="kentZaaks">
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DocumentUpdate;
