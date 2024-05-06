import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMutatie } from 'app/shared/model/mutatie.model';
import { getEntities as getMutaties } from 'app/entities/mutatie/mutatie.reducer';
import { IBatch } from 'app/shared/model/batch.model';
import { getEntities as getBatches } from 'app/entities/batch/batch.reducer';
import { IBatchregel } from 'app/shared/model/batchregel.model';
import { getEntity, updateEntity, createEntity, reset } from './batchregel.reducer';

export const BatchregelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mutaties = useAppSelector(state => state.mutatie.entities);
  const batches = useAppSelector(state => state.batch.entities);
  const batchregelEntity = useAppSelector(state => state.batchregel.entity);
  const loading = useAppSelector(state => state.batchregel.loading);
  const updating = useAppSelector(state => state.batchregel.updating);
  const updateSuccess = useAppSelector(state => state.batchregel.updateSuccess);

  const handleClose = () => {
    navigate('/batchregel');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMutaties({}));
    dispatch(getBatches({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...batchregelEntity,
      ...values,
      leidttotMutatie: mutaties.find(it => it.id.toString() === values.leidttotMutatie?.toString()),
      heeftBatch: batches.find(it => it.id.toString() === values.heeftBatch?.toString()),
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
          ...batchregelEntity,
          leidttotMutatie: batchregelEntity?.leidttotMutatie?.id,
          heeftBatch: batchregelEntity?.heeftBatch?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.batchregel.home.createOrEditLabel" data-cy="BatchregelCreateUpdateHeading">
            Create or edit a Batchregel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="batchregel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="batchregel-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField
                label="Datumbetaling"
                id="batchregel-datumbetaling"
                name="datumbetaling"
                data-cy="datumbetaling"
                type="date"
              />
              <ValidatedField label="Omschrijving" id="batchregel-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Rekeningnaar" id="batchregel-rekeningnaar" name="rekeningnaar" data-cy="rekeningnaar" type="text" />
              <ValidatedField label="Rekeningvan" id="batchregel-rekeningvan" name="rekeningvan" data-cy="rekeningvan" type="text" />
              <ValidatedField
                id="batchregel-leidttotMutatie"
                name="leidttotMutatie"
                data-cy="leidttotMutatie"
                label="Leidttot Mutatie"
                type="select"
              >
                <option value="" key="0" />
                {mutaties
                  ? mutaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="batchregel-heeftBatch" name="heeftBatch" data-cy="heeftBatch" label="Heeft Batch" type="select">
                <option value="" key="0" />
                {batches
                  ? batches.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/batchregel" replace color="info">
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

export default BatchregelUpdate;
