import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDeelprocestype } from 'app/shared/model/deelprocestype.model';
import { getEntities as getDeelprocestypes } from 'app/entities/deelprocestype/deelprocestype.reducer';
import { IDeelproces } from 'app/shared/model/deelproces.model';
import { getEntity, updateEntity, createEntity, reset } from './deelproces.reducer';

export const DeelprocesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const deelprocestypes = useAppSelector(state => state.deelprocestype.entities);
  const deelprocesEntity = useAppSelector(state => state.deelproces.entity);
  const loading = useAppSelector(state => state.deelproces.loading);
  const updating = useAppSelector(state => state.deelproces.updating);
  const updateSuccess = useAppSelector(state => state.deelproces.updateSuccess);

  const handleClose = () => {
    navigate('/deelproces');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDeelprocestypes({}));
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
      ...deelprocesEntity,
      ...values,
      isvanDeelprocestype: deelprocestypes.find(it => it.id.toString() === values.isvanDeelprocestype?.toString()),
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
          ...deelprocesEntity,
          isvanDeelprocestype: deelprocesEntity?.isvanDeelprocestype?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.deelproces.home.createOrEditLabel" data-cy="DeelprocesCreateUpdateHeading">
            Create or edit a Deelproces
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="deelproces-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumafgehandeld"
                id="deelproces-datumafgehandeld"
                name="datumafgehandeld"
                data-cy="datumafgehandeld"
                type="date"
              />
              <ValidatedField label="Datumgepland" id="deelproces-datumgepland" name="datumgepland" data-cy="datumgepland" type="date" />
              <ValidatedField
                id="deelproces-isvanDeelprocestype"
                name="isvanDeelprocestype"
                data-cy="isvanDeelprocestype"
                label="Isvan Deelprocestype"
                type="select"
                required
              >
                <option value="" key="0" />
                {deelprocestypes
                  ? deelprocestypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deelproces" replace color="info">
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

export default DeelprocesUpdate;
