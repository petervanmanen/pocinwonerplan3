import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { getEntities as getVastgoedobjects } from 'app/entities/vastgoedobject/vastgoedobject.reducer';
import { IBouwdeel } from 'app/shared/model/bouwdeel.model';
import { getEntities as getBouwdeels } from 'app/entities/bouwdeel/bouwdeel.reducer';
import { IBouwdeelelement } from 'app/shared/model/bouwdeelelement.model';
import { getEntities as getBouwdeelelements } from 'app/entities/bouwdeelelement/bouwdeelelement.reducer';
import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { getEntities as getInkooporders } from 'app/entities/inkooporder/inkooporder.reducer';
import { ILeverancier } from 'app/shared/model/leverancier.model';
import { getEntities as getLeveranciers } from 'app/entities/leverancier/leverancier.reducer';
import { IWerkbon } from 'app/shared/model/werkbon.model';
import { getEntity, updateEntity, createEntity, reset } from './werkbon.reducer';

export const WerkbonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedobjects = useAppSelector(state => state.vastgoedobject.entities);
  const bouwdeels = useAppSelector(state => state.bouwdeel.entities);
  const bouwdeelelements = useAppSelector(state => state.bouwdeelelement.entities);
  const inkooporders = useAppSelector(state => state.inkooporder.entities);
  const leveranciers = useAppSelector(state => state.leverancier.entities);
  const werkbonEntity = useAppSelector(state => state.werkbon.entity);
  const loading = useAppSelector(state => state.werkbon.loading);
  const updating = useAppSelector(state => state.werkbon.updating);
  const updateSuccess = useAppSelector(state => state.werkbon.updateSuccess);

  const handleClose = () => {
    navigate('/werkbon');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVastgoedobjects({}));
    dispatch(getBouwdeels({}));
    dispatch(getBouwdeelelements({}));
    dispatch(getInkooporders({}));
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
      ...werkbonEntity,
      ...values,
      betreftVastgoedobject: vastgoedobjects.find(it => it.id.toString() === values.betreftVastgoedobject?.toString()),
      betreftBouwdeels: mapIdList(values.betreftBouwdeels),
      betreftBouwdeelelements: mapIdList(values.betreftBouwdeelelements),
      hoortbijInkooporder: inkooporders.find(it => it.id.toString() === values.hoortbijInkooporder?.toString()),
      voertwerkuitconformLeverancier: leveranciers.find(it => it.id.toString() === values.voertwerkuitconformLeverancier?.toString()),
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
          ...werkbonEntity,
          betreftVastgoedobject: werkbonEntity?.betreftVastgoedobject?.id,
          betreftBouwdeels: werkbonEntity?.betreftBouwdeels?.map(e => e.id.toString()),
          betreftBouwdeelelements: werkbonEntity?.betreftBouwdeelelements?.map(e => e.id.toString()),
          hoortbijInkooporder: werkbonEntity?.hoortbijInkooporder?.id,
          voertwerkuitconformLeverancier: werkbonEntity?.voertwerkuitconformLeverancier?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.werkbon.home.createOrEditLabel" data-cy="WerkbonCreateUpdateHeading">
            Create or edit a Werkbon
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="werkbon-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                id="werkbon-betreftVastgoedobject"
                name="betreftVastgoedobject"
                data-cy="betreftVastgoedobject"
                label="Betreft Vastgoedobject"
                type="select"
              >
                <option value="" key="0" />
                {vastgoedobjects
                  ? vastgoedobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Bouwdeel"
                id="werkbon-betreftBouwdeel"
                data-cy="betreftBouwdeel"
                type="select"
                multiple
                name="betreftBouwdeels"
              >
                <option value="" key="0" />
                {bouwdeels
                  ? bouwdeels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Betreft Bouwdeelelement"
                id="werkbon-betreftBouwdeelelement"
                data-cy="betreftBouwdeelelement"
                type="select"
                multiple
                name="betreftBouwdeelelements"
              >
                <option value="" key="0" />
                {bouwdeelelements
                  ? bouwdeelelements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="werkbon-hoortbijInkooporder"
                name="hoortbijInkooporder"
                data-cy="hoortbijInkooporder"
                label="Hoortbij Inkooporder"
                type="select"
              >
                <option value="" key="0" />
                {inkooporders
                  ? inkooporders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="werkbon-voertwerkuitconformLeverancier"
                name="voertwerkuitconformLeverancier"
                data-cy="voertwerkuitconformLeverancier"
                label="Voertwerkuitconform Leverancier"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/werkbon" replace color="info">
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

export default WerkbonUpdate;
