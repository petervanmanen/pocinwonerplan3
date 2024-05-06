import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFormuliersoortveld } from 'app/shared/model/formuliersoortveld.model';
import { getEntities as getFormuliersoortvelds } from 'app/entities/formuliersoortveld/formuliersoortveld.reducer';
import { IAanvraagdata } from 'app/shared/model/aanvraagdata.model';
import { getEntity, updateEntity, createEntity, reset } from './aanvraagdata.reducer';

export const AanvraagdataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const formuliersoortvelds = useAppSelector(state => state.formuliersoortveld.entities);
  const aanvraagdataEntity = useAppSelector(state => state.aanvraagdata.entity);
  const loading = useAppSelector(state => state.aanvraagdata.loading);
  const updating = useAppSelector(state => state.aanvraagdata.updating);
  const updateSuccess = useAppSelector(state => state.aanvraagdata.updateSuccess);

  const handleClose = () => {
    navigate('/aanvraagdata');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getFormuliersoortvelds({}));
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
      ...aanvraagdataEntity,
      ...values,
      isconformFormuliersoortveld: formuliersoortvelds.find(it => it.id.toString() === values.isconformFormuliersoortveld?.toString()),
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
          ...aanvraagdataEntity,
          isconformFormuliersoortveld: aanvraagdataEntity?.isconformFormuliersoortveld?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanvraagdata.home.createOrEditLabel" data-cy="AanvraagdataCreateUpdateHeading">
            Create or edit a Aanvraagdata
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aanvraagdata-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Data" id="aanvraagdata-data" name="data" data-cy="data" type="text" />
              <ValidatedField label="Veld" id="aanvraagdata-veld" name="veld" data-cy="veld" type="text" />
              <ValidatedField
                id="aanvraagdata-isconformFormuliersoortveld"
                name="isconformFormuliersoortveld"
                data-cy="isconformFormuliersoortveld"
                label="Isconform Formuliersoortveld"
                type="select"
              >
                <option value="" key="0" />
                {formuliersoortvelds
                  ? formuliersoortvelds.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanvraagdata" replace color="info">
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

export default AanvraagdataUpdate;
