import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { IInstructieregel } from 'app/shared/model/instructieregel.model';
import { getEntities as getInstructieregels } from 'app/entities/instructieregel/instructieregel.reducer';
import { IGebiedsaanwijzing } from 'app/shared/model/gebiedsaanwijzing.model';
import { getEntity, updateEntity, createEntity, reset } from './gebiedsaanwijzing.reducer';

export const GebiedsaanwijzingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locaties = useAppSelector(state => state.locatie.entities);
  const instructieregels = useAppSelector(state => state.instructieregel.entities);
  const gebiedsaanwijzingEntity = useAppSelector(state => state.gebiedsaanwijzing.entity);
  const loading = useAppSelector(state => state.gebiedsaanwijzing.loading);
  const updating = useAppSelector(state => state.gebiedsaanwijzing.updating);
  const updateSuccess = useAppSelector(state => state.gebiedsaanwijzing.updateSuccess);

  const handleClose = () => {
    navigate('/gebiedsaanwijzing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocaties({}));
    dispatch(getInstructieregels({}));
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
      ...gebiedsaanwijzingEntity,
      ...values,
      verwijstnaarLocaties: mapIdList(values.verwijstnaarLocaties),
      beschrijftgebiedsaanwijzingInstructieregels: mapIdList(values.beschrijftgebiedsaanwijzingInstructieregels),
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
          ...gebiedsaanwijzingEntity,
          verwijstnaarLocaties: gebiedsaanwijzingEntity?.verwijstnaarLocaties?.map(e => e.id.toString()),
          beschrijftgebiedsaanwijzingInstructieregels: gebiedsaanwijzingEntity?.beschrijftgebiedsaanwijzingInstructieregels?.map(e =>
            e.id.toString(),
          ),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gebiedsaanwijzing.home.createOrEditLabel" data-cy="GebiedsaanwijzingCreateUpdateHeading">
            Create or edit a Gebiedsaanwijzing
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
                <ValidatedField name="id" required readOnly id="gebiedsaanwijzing-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Groep" id="gebiedsaanwijzing-groep" name="groep" data-cy="groep" type="text" />
              <ValidatedField label="Naam" id="gebiedsaanwijzing-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Nen 3610 Id" id="gebiedsaanwijzing-nen3610id" name="nen3610id" data-cy="nen3610id" type="text" />
              <ValidatedField
                label="Verwijstnaar Locatie"
                id="gebiedsaanwijzing-verwijstnaarLocatie"
                data-cy="verwijstnaarLocatie"
                type="select"
                multiple
                name="verwijstnaarLocaties"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Beschrijftgebiedsaanwijzing Instructieregel"
                id="gebiedsaanwijzing-beschrijftgebiedsaanwijzingInstructieregel"
                data-cy="beschrijftgebiedsaanwijzingInstructieregel"
                type="select"
                multiple
                name="beschrijftgebiedsaanwijzingInstructieregels"
              >
                <option value="" key="0" />
                {instructieregels
                  ? instructieregels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gebiedsaanwijzing" replace color="info">
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

export default GebiedsaanwijzingUpdate;
