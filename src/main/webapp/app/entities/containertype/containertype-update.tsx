import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVuilniswagen } from 'app/shared/model/vuilniswagen.model';
import { getEntities as getVuilniswagens } from 'app/entities/vuilniswagen/vuilniswagen.reducer';
import { IContainertype } from 'app/shared/model/containertype.model';
import { getEntity, updateEntity, createEntity, reset } from './containertype.reducer';

export const ContainertypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vuilniswagens = useAppSelector(state => state.vuilniswagen.entities);
  const containertypeEntity = useAppSelector(state => state.containertype.entity);
  const loading = useAppSelector(state => state.containertype.loading);
  const updating = useAppSelector(state => state.containertype.updating);
  const updateSuccess = useAppSelector(state => state.containertype.updateSuccess);

  const handleClose = () => {
    navigate('/containertype');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVuilniswagens({}));
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
      ...containertypeEntity,
      ...values,
      geschiktvoorVuilniswagens: mapIdList(values.geschiktvoorVuilniswagens),
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
          ...containertypeEntity,
          geschiktvoorVuilniswagens: containertypeEntity?.geschiktvoorVuilniswagens?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.containertype.home.createOrEditLabel" data-cy="ContainertypeCreateUpdateHeading">
            Create or edit a Containertype
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
                <ValidatedField name="id" required readOnly id="containertype-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Naam" id="containertype-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="containertype-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Geschiktvoor Vuilniswagen"
                id="containertype-geschiktvoorVuilniswagen"
                data-cy="geschiktvoorVuilniswagen"
                type="select"
                multiple
                name="geschiktvoorVuilniswagens"
              >
                <option value="" key="0" />
                {vuilniswagens
                  ? vuilniswagens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/containertype" replace color="info">
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

export default ContainertypeUpdate;
