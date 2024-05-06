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
import { IVerkeersbesluit } from 'app/shared/model/verkeersbesluit.model';
import { getEntity, updateEntity, createEntity, reset } from './verkeersbesluit.reducer';

export const VerkeersbesluitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const documents = useAppSelector(state => state.document.entities);
  const verkeersbesluitEntity = useAppSelector(state => state.verkeersbesluit.entity);
  const loading = useAppSelector(state => state.verkeersbesluit.loading);
  const updating = useAppSelector(state => state.verkeersbesluit.updating);
  const updateSuccess = useAppSelector(state => state.verkeersbesluit.updateSuccess);

  const handleClose = () => {
    navigate('/verkeersbesluit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDocuments({}));
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
      ...verkeersbesluitEntity,
      ...values,
      isvastgelegdinDocument: documents.find(it => it.id.toString() === values.isvastgelegdinDocument?.toString()),
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
          ...verkeersbesluitEntity,
          isvastgelegdinDocument: verkeersbesluitEntity?.isvastgelegdinDocument?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.verkeersbesluit.home.createOrEditLabel" data-cy="VerkeersbesluitCreateUpdateHeading">
            Create or edit a Verkeersbesluit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="verkeersbesluit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Datumbesluit"
                id="verkeersbesluit-datumbesluit"
                name="datumbesluit"
                data-cy="datumbesluit"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="verkeersbesluit-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField label="Datumstart" id="verkeersbesluit-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField label="Huisnummer" id="verkeersbesluit-huisnummer" name="huisnummer" data-cy="huisnummer" type="text" />
              <ValidatedField label="Postcode" id="verkeersbesluit-postcode" name="postcode" data-cy="postcode" type="text" />
              <ValidatedField
                label="Referentienummer"
                id="verkeersbesluit-referentienummer"
                name="referentienummer"
                data-cy="referentienummer"
                type="text"
              />
              <ValidatedField label="Straat" id="verkeersbesluit-straat" name="straat" data-cy="straat" type="text" />
              <ValidatedField label="Titel" id="verkeersbesluit-titel" name="titel" data-cy="titel" type="text" />
              <ValidatedField
                id="verkeersbesluit-isvastgelegdinDocument"
                name="isvastgelegdinDocument"
                data-cy="isvastgelegdinDocument"
                label="Isvastgelegdin Document"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/verkeersbesluit" replace color="info">
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

export default VerkeersbesluitUpdate;
