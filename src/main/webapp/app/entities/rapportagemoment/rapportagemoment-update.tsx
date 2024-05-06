import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubsidie } from 'app/shared/model/subsidie.model';
import { getEntities as getSubsidies } from 'app/entities/subsidie/subsidie.reducer';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { getEntities as getRechtspersoons } from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import { IRapportagemoment } from 'app/shared/model/rapportagemoment.model';
import { getEntity, updateEntity, createEntity, reset } from './rapportagemoment.reducer';

export const RapportagemomentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subsidies = useAppSelector(state => state.subsidie.entities);
  const rechtspersoons = useAppSelector(state => state.rechtspersoon.entities);
  const rapportagemomentEntity = useAppSelector(state => state.rapportagemoment.entity);
  const loading = useAppSelector(state => state.rapportagemoment.loading);
  const updating = useAppSelector(state => state.rapportagemoment.updating);
  const updateSuccess = useAppSelector(state => state.rapportagemoment.updateSuccess);

  const handleClose = () => {
    navigate('/rapportagemoment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubsidies({}));
    dispatch(getRechtspersoons({}));
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
      ...rapportagemomentEntity,
      ...values,
      heeftSubsidie: subsidies.find(it => it.id.toString() === values.heeftSubsidie?.toString()),
      projectleiderRechtspersoon: rechtspersoons.find(it => it.id.toString() === values.projectleiderRechtspersoon?.toString()),
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
          ...rapportagemomentEntity,
          heeftSubsidie: rapportagemomentEntity?.heeftSubsidie?.id,
          projectleiderRechtspersoon: rapportagemomentEntity?.projectleiderRechtspersoon?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.rapportagemoment.home.createOrEditLabel" data-cy="RapportagemomentCreateUpdateHeading">
            Create or edit a Rapportagemoment
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
                <ValidatedField name="id" required readOnly id="rapportagemoment-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Datum" id="rapportagemoment-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Naam" id="rapportagemoment-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="rapportagemoment-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Termijn" id="rapportagemoment-termijn" name="termijn" data-cy="termijn" type="text" />
              <ValidatedField
                id="rapportagemoment-heeftSubsidie"
                name="heeftSubsidie"
                data-cy="heeftSubsidie"
                label="Heeft Subsidie"
                type="select"
              >
                <option value="" key="0" />
                {subsidies
                  ? subsidies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="rapportagemoment-projectleiderRechtspersoon"
                name="projectleiderRechtspersoon"
                data-cy="projectleiderRechtspersoon"
                label="Projectleider Rechtspersoon"
                type="select"
              >
                <option value="" key="0" />
                {rechtspersoons
                  ? rechtspersoons.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rapportagemoment" replace color="info">
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

export default RapportagemomentUpdate;
