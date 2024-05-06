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
import { IRit } from 'app/shared/model/rit.model';
import { getEntity, updateEntity, createEntity, reset } from './rit.reducer';

export const RitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vuilniswagens = useAppSelector(state => state.vuilniswagen.entities);
  const ritEntity = useAppSelector(state => state.rit.entity);
  const loading = useAppSelector(state => state.rit.loading);
  const updating = useAppSelector(state => state.rit.updating);
  const updateSuccess = useAppSelector(state => state.rit.updateSuccess);

  const handleClose = () => {
    navigate('/rit');
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
      ...ritEntity,
      ...values,
      uitgevoerdmetVuilniswagen: vuilniswagens.find(it => it.id.toString() === values.uitgevoerdmetVuilniswagen?.toString()),
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
          ...ritEntity,
          uitgevoerdmetVuilniswagen: ritEntity?.uitgevoerdmetVuilniswagen?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rit.home.createOrEditLabel" data-cy="RitCreateUpdateHeading">
            Create or edit a Rit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="rit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Eindtijd" id="rit-eindtijd" name="eindtijd" data-cy="eindtijd" type="text" />
              <ValidatedField label="Ritcode" id="rit-ritcode" name="ritcode" data-cy="ritcode" type="text" />
              <ValidatedField label="Starttijd" id="rit-starttijd" name="starttijd" data-cy="starttijd" type="text" />
              <ValidatedField
                id="rit-uitgevoerdmetVuilniswagen"
                name="uitgevoerdmetVuilniswagen"
                data-cy="uitgevoerdmetVuilniswagen"
                label="Uitgevoerdmet Vuilniswagen"
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rit" replace color="info">
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

export default RitUpdate;
