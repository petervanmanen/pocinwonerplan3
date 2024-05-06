import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntities as getZaaks } from 'app/entities/zaak/zaak.reducer';
import { IBesluittype } from 'app/shared/model/besluittype.model';
import { getEntities as getBesluittypes } from 'app/entities/besluittype/besluittype.reducer';
import { IBesluit } from 'app/shared/model/besluit.model';
import { getEntity, updateEntity, createEntity, reset } from './besluit.reducer';

export const BesluitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documents = useAppSelector(state => state.document.entities);
  const zaaks = useAppSelector(state => state.zaak.entities);
  const besluittypes = useAppSelector(state => state.besluittype.entities);
  const besluitEntity = useAppSelector(state => state.besluit.entity);
  const loading = useAppSelector(state => state.besluit.loading);
  const updating = useAppSelector(state => state.besluit.updating);
  const updateSuccess = useAppSelector(state => state.besluit.updateSuccess);

  const handleClose = () => {
    navigate('/besluit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDocuments({}));
    dispatch(getZaaks({}));
    dispatch(getBesluittypes({}));
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
      ...besluitEntity,
      ...values,
      isvastgelegdinDocument: documents.find(it => it.id.toString() === values.isvastgelegdinDocument?.toString()),
      isuitkomstvanZaak: zaaks.find(it => it.id.toString() === values.isuitkomstvanZaak?.toString()),
      isvanBesluittype: besluittypes.find(it => it.id.toString() === values.isvanBesluittype?.toString()),
      kanvastgelegdzijnalsDocuments: mapIdList(values.kanvastgelegdzijnalsDocuments),
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
          ...besluitEntity,
          isvastgelegdinDocument: besluitEntity?.isvastgelegdinDocument?.id,
          isuitkomstvanZaak: besluitEntity?.isuitkomstvanZaak?.id,
          isvanBesluittype: besluitEntity?.isvanBesluittype?.id,
          kanvastgelegdzijnalsDocuments: besluitEntity?.kanvastgelegdzijnalsDocuments?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.besluit.home.createOrEditLabel" data-cy="BesluitCreateUpdateHeading">
            Create or edit a Besluit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="besluit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Besluit" id="besluit-besluit" name="besluit" data-cy="besluit" type="text" />
              <ValidatedField
                label="Besluitidentificatie"
                id="besluit-besluitidentificatie"
                name="besluitidentificatie"
                data-cy="besluitidentificatie"
                type="text"
              />
              <ValidatedField
                label="Besluittoelichting"
                id="besluit-besluittoelichting"
                name="besluittoelichting"
                data-cy="besluittoelichting"
                type="text"
              />
              <ValidatedField label="Datumbesluit" id="besluit-datumbesluit" name="datumbesluit" data-cy="datumbesluit" type="text" />
              <ValidatedField
                label="Datumpublicatie"
                id="besluit-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField label="Datumstart" id="besluit-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumuiterlijkereactie"
                id="besluit-datumuiterlijkereactie"
                name="datumuiterlijkereactie"
                data-cy="datumuiterlijkereactie"
                type="text"
              />
              <ValidatedField label="Datumverval" id="besluit-datumverval" name="datumverval" data-cy="datumverval" type="text" />
              <ValidatedField
                label="Datumverzending"
                id="besluit-datumverzending"
                name="datumverzending"
                data-cy="datumverzending"
                type="text"
              />
              <ValidatedField label="Redenverval" id="besluit-redenverval" name="redenverval" data-cy="redenverval" type="text" />
              <ValidatedField
                id="besluit-isvastgelegdinDocument"
                name="isvastgelegdinDocument"
                data-cy="isvastgelegdinDocument"
                label="Isvastgelegdin Document"
                type="select"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="besluit-isuitkomstvanZaak"
                name="isuitkomstvanZaak"
                data-cy="isuitkomstvanZaak"
                label="Isuitkomstvan Zaak"
                type="select"
              >
                <option value="" key="0" />
                {zaaks
                  ? zaaks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="besluit-isvanBesluittype"
                name="isvanBesluittype"
                data-cy="isvanBesluittype"
                label="Isvan Besluittype"
                type="select"
              >
                <option value="" key="0" />
                {besluittypes
                  ? besluittypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Kanvastgelegdzijnals Document"
                id="besluit-kanvastgelegdzijnalsDocument"
                data-cy="kanvastgelegdzijnalsDocument"
                type="select"
                multiple
                name="kanvastgelegdzijnalsDocuments"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/besluit" replace color="info">
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

export default BesluitUpdate;
