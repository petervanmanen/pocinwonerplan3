import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContainertype } from 'app/shared/model/containertype.model';
import { getEntities as getContainertypes } from 'app/entities/containertype/containertype.reducer';
import { IVuilniswagen } from 'app/shared/model/vuilniswagen.model';
import { getEntity, updateEntity, createEntity, reset } from './vuilniswagen.reducer';

export const VuilniswagenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const containertypes = useAppSelector(state => state.containertype.entities);
  const vuilniswagenEntity = useAppSelector(state => state.vuilniswagen.entity);
  const loading = useAppSelector(state => state.vuilniswagen.loading);
  const updating = useAppSelector(state => state.vuilniswagen.updating);
  const updateSuccess = useAppSelector(state => state.vuilniswagen.updateSuccess);

  const handleClose = () => {
    navigate('/vuilniswagen');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContainertypes({}));
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
      ...vuilniswagenEntity,
      ...values,
      geschiktvoorContainertypes: mapIdList(values.geschiktvoorContainertypes),
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
          ...vuilniswagenEntity,
          geschiktvoorContainertypes: vuilniswagenEntity?.geschiktvoorContainertypes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vuilniswagen.home.createOrEditLabel" data-cy="VuilniswagenCreateUpdateHeading">
            Create or edit a Vuilniswagen
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vuilniswagen-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="vuilniswagen-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Kenteken"
                id="vuilniswagen-kenteken"
                name="kenteken"
                data-cy="kenteken"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField label="Type" id="vuilniswagen-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Geschiktvoor Containertype"
                id="vuilniswagen-geschiktvoorContainertype"
                data-cy="geschiktvoorContainertype"
                type="select"
                multiple
                name="geschiktvoorContainertypes"
              >
                <option value="" key="0" />
                {containertypes
                  ? containertypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vuilniswagen" replace color="info">
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

export default VuilniswagenUpdate;
