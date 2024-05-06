import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFormuliersoort } from 'app/shared/model/formuliersoort.model';
import { getEntities as getFormuliersoorts } from 'app/entities/formuliersoort/formuliersoort.reducer';
import { IFormuliersoortveld } from 'app/shared/model/formuliersoortveld.model';
import { getEntity, updateEntity, createEntity, reset } from './formuliersoortveld.reducer';

export const FormuliersoortveldUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const formuliersoorts = useAppSelector(state => state.formuliersoort.entities);
  const formuliersoortveldEntity = useAppSelector(state => state.formuliersoortveld.entity);
  const loading = useAppSelector(state => state.formuliersoortveld.loading);
  const updating = useAppSelector(state => state.formuliersoortveld.updating);
  const updateSuccess = useAppSelector(state => state.formuliersoortveld.updateSuccess);

  const handleClose = () => {
    navigate('/formuliersoortveld');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFormuliersoorts({}));
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
      ...formuliersoortveldEntity,
      ...values,
      heeftveldenFormuliersoort: formuliersoorts.find(it => it.id.toString() === values.heeftveldenFormuliersoort?.toString()),
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
          ...formuliersoortveldEntity,
          heeftveldenFormuliersoort: formuliersoortveldEntity?.heeftveldenFormuliersoort?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.formuliersoortveld.home.createOrEditLabel" data-cy="FormuliersoortveldCreateUpdateHeading">
            Create or edit a Formuliersoortveld
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
                <ValidatedField name="id" required readOnly id="formuliersoortveld-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Helptekst" id="formuliersoortveld-helptekst" name="helptekst" data-cy="helptekst" type="text" />
              <ValidatedField
                label="Isverplicht"
                id="formuliersoortveld-isverplicht"
                name="isverplicht"
                data-cy="isverplicht"
                check
                type="checkbox"
              />
              <ValidatedField label="Label" id="formuliersoortveld-label" name="label" data-cy="label" type="text" />
              <ValidatedField label="Maxlengte" id="formuliersoortveld-maxlengte" name="maxlengte" data-cy="maxlengte" type="text" />
              <ValidatedField label="Veldnaam" id="formuliersoortveld-veldnaam" name="veldnaam" data-cy="veldnaam" type="text" />
              <ValidatedField label="Veldtype" id="formuliersoortveld-veldtype" name="veldtype" data-cy="veldtype" type="text" />
              <ValidatedField
                id="formuliersoortveld-heeftveldenFormuliersoort"
                name="heeftveldenFormuliersoort"
                data-cy="heeftveldenFormuliersoort"
                label="Heeftvelden Formuliersoort"
                type="select"
              >
                <option value="" key="0" />
                {formuliersoorts
                  ? formuliersoorts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/formuliersoortveld" replace color="info">
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

export default FormuliersoortveldUpdate;
