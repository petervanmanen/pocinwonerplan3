import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { getEntities as getAanbestedings } from 'app/entities/aanbesteding/aanbesteding.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IKwalificatie } from 'app/shared/model/kwalificatie.model';
import { getEntity, updateEntity, createEntity, reset } from './kwalificatie.reducer';

export const KwalificatieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanbestedings = useAppSelector(state => state.aanbesteding.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const kwalificatieEntity = useAppSelector(state => state.kwalificatie.entity);
  const loading = useAppSelector(state => state.kwalificatie.loading);
  const updating = useAppSelector(state => state.kwalificatie.updating);
  const updateSuccess = useAppSelector(state => state.kwalificatie.updateSuccess);

  const handleClose = () => {
    navigate('/kwalificatie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAanbestedings({}));
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
      ...kwalificatieEntity,
      ...values,
      betreftAanbesteding: aanbestedings.find(it => it.id.toString() === values.betreftAanbesteding?.toString()),
      heeftLeverancier: leveranciers.find(it => it.id.toString() === values.heeftLeverancier?.toString()),
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
          ...kwalificatieEntity,
          betreftAanbesteding: kwalificatieEntity?.betreftAanbesteding?.id,
          heeftLeverancier: kwalificatieEntity?.heeftLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.kwalificatie.home.createOrEditLabel" data-cy="KwalificatieCreateUpdateHeading">
            Create or edit a Kwalificatie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="kwalificatie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Eindegeldigheid"
                id="kwalificatie-eindegeldigheid"
                name="eindegeldigheid"
                data-cy="eindegeldigheid"
                type="date"
              />
              <ValidatedField
                label="Startgeldigheid"
                id="kwalificatie-startgeldigheid"
                name="startgeldigheid"
                data-cy="startgeldigheid"
                type="date"
              />
              <ValidatedField
                id="kwalificatie-betreftAanbesteding"
                name="betreftAanbesteding"
                data-cy="betreftAanbesteding"
                label="Betreft Aanbesteding"
                type="select"
              >
                <option value="" key="0" />
                {aanbestedings
                  ? aanbestedings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="kwalificatie-heeftLeverancier"
                name="heeftLeverancier"
                data-cy="heeftLeverancier"
                label="Heeft Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/kwalificatie" replace color="info">
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

export default KwalificatieUpdate;
