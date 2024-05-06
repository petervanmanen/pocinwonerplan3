import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IUitnodiging } from 'app/shared/model/uitnodiging.model';
import { getEntity, updateEntity, createEntity, reset } from './uitnodiging.reducer';

export const UitnodigingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const uitnodigingEntity = useAppSelector(state => state.uitnodiging.entity);
  const loading = useAppSelector(state => state.uitnodiging.loading);
  const updating = useAppSelector(state => state.uitnodiging.updating);
  const updateSuccess = useAppSelector(state => state.uitnodiging.updateSuccess);

  const handleClose = () => {
    navigate('/uitnodiging');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLeveranciers({}));
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
      ...uitnodigingEntity,
      ...values,
      gerichtaanLeverancier: leveranciers.find(it => it.id.toString() === values.gerichtaanLeverancier?.toString()),
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
          ...uitnodigingEntity,
          gerichtaanLeverancier: uitnodigingEntity?.gerichtaanLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.uitnodiging.home.createOrEditLabel" data-cy="UitnodigingCreateUpdateHeading">
            Create or edit a Uitnodiging
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="uitnodiging-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Afgewezen" id="uitnodiging-afgewezen" name="afgewezen" data-cy="afgewezen" type="text" />
              <ValidatedField label="Datum" id="uitnodiging-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField label="Geaccepteerd" id="uitnodiging-geaccepteerd" name="geaccepteerd" data-cy="geaccepteerd" type="text" />
              <ValidatedField
                id="uitnodiging-gerichtaanLeverancier"
                name="gerichtaanLeverancier"
                data-cy="gerichtaanLeverancier"
                label="Gerichtaan Leverancier"
                type="select"
              >
                <option value="" key="0" />
                {leveranciers
                  ? leveranciers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/uitnodiging" replace color="info">
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

export default UitnodigingUpdate;
