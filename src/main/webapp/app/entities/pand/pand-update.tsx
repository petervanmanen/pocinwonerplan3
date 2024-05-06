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
import { IBuurt } from 'app/shared/model/buurt.model';
import { getEntities as getBuurts } from 'app/entities/buurt/buurt.reducer';
import { IVerblijfsobject } from 'app/shared/model/verblijfsobject.model';
import { getEntities as getVerblijfsobjects } from 'app/entities/verblijfsobject/verblijfsobject.reducer';
import { IPand } from 'app/shared/model/pand.model';
import { getEntity, updateEntity, createEntity, reset } from './pand.reducer';

export const PandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedobjects = useAppSelector(state => state.vastgoedobject.entities);
  const buurts = useAppSelector(state => state.buurt.entities);
  const verblijfsobjects = useAppSelector(state => state.verblijfsobject.entities);
  const pandEntity = useAppSelector(state => state.pand.entity);
  const loading = useAppSelector(state => state.pand.loading);
  const updating = useAppSelector(state => state.pand.updating);
  const updateSuccess = useAppSelector(state => state.pand.updateSuccess);

  const handleClose = () => {
    navigate('/pand');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVastgoedobjects({}));
    dispatch(getBuurts({}));
    dispatch(getVerblijfsobjects({}));
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
      ...pandEntity,
      ...values,
      heeftVastgoedobject: vastgoedobjects.find(it => it.id.toString() === values.heeftVastgoedobject?.toString()),
      zonderverblijfsobjectligtinBuurt: buurts.find(it => it.id.toString() === values.zonderverblijfsobjectligtinBuurt?.toString()),
      maaktdeeluitvanVerblijfsobjects: mapIdList(values.maaktdeeluitvanVerblijfsobjects),
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
          ...pandEntity,
          heeftVastgoedobject: pandEntity?.heeftVastgoedobject?.id,
          zonderverblijfsobjectligtinBuurt: pandEntity?.zonderverblijfsobjectligtinBuurt?.id,
          maaktdeeluitvanVerblijfsobjects: pandEntity?.maaktdeeluitvanVerblijfsobjects?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.pand.home.createOrEditLabel" data-cy="PandCreateUpdateHeading">
            Create or edit a Pand
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="pand-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Brutoinhoudpand"
                id="pand-brutoinhoudpand"
                name="brutoinhoudpand"
                data-cy="brutoinhoudpand"
                type="text"
              />
              <ValidatedField
                label="Datumbegingeldigheid"
                id="pand-datumbegingeldigheid"
                name="datumbegingeldigheid"
                data-cy="datumbegingeldigheid"
                type="date"
              />
              <ValidatedField label="Datumeinde" id="pand-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumeindegeldigheid"
                id="pand-datumeindegeldigheid"
                name="datumeindegeldigheid"
                data-cy="datumeindegeldigheid"
                type="date"
              />
              <ValidatedField label="Datumingang" id="pand-datumingang" name="datumingang" data-cy="datumingang" type="date" />
              <ValidatedField
                label="Geconstateerd"
                id="pand-geconstateerd"
                name="geconstateerd"
                data-cy="geconstateerd"
                check
                type="checkbox"
              />
              <ValidatedField
                label="Geometriebovenaanzicht"
                id="pand-geometriebovenaanzicht"
                name="geometriebovenaanzicht"
                data-cy="geometriebovenaanzicht"
                type="text"
              />
              <ValidatedField
                label="Geometriemaaiveld"
                id="pand-geometriemaaiveld"
                name="geometriemaaiveld"
                data-cy="geometriemaaiveld"
                type="text"
              />
              <ValidatedField label="Geometriepunt" id="pand-geometriepunt" name="geometriepunt" data-cy="geometriepunt" type="text" />
              <ValidatedField
                label="Hoogstebouwlaagpand"
                id="pand-hoogstebouwlaagpand"
                name="hoogstebouwlaagpand"
                data-cy="hoogstebouwlaagpand"
                type="text"
              />
              <ValidatedField label="Identificatie" id="pand-identificatie" name="identificatie" data-cy="identificatie" type="text" />
              <ValidatedField label="Inonderzoek" id="pand-inonderzoek" name="inonderzoek" data-cy="inonderzoek" check type="checkbox" />
              <ValidatedField
                label="Laagstebouwlaagpand"
                id="pand-laagstebouwlaagpand"
                name="laagstebouwlaagpand"
                data-cy="laagstebouwlaagpand"
                type="text"
              />
              <ValidatedField
                label="Oorspronkelijkbouwjaar"
                id="pand-oorspronkelijkbouwjaar"
                name="oorspronkelijkbouwjaar"
                data-cy="oorspronkelijkbouwjaar"
                type="text"
              />
              <ValidatedField label="Oppervlakte" id="pand-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField
                label="Relatievehoogteliggingpand"
                id="pand-relatievehoogteliggingpand"
                name="relatievehoogteliggingpand"
                data-cy="relatievehoogteliggingpand"
                type="text"
              />
              <ValidatedField label="Status" id="pand-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Statusvoortgangbouw"
                id="pand-statusvoortgangbouw"
                name="statusvoortgangbouw"
                data-cy="statusvoortgangbouw"
                type="text"
              />
              <ValidatedField label="Versie" id="pand-versie" name="versie" data-cy="versie" type="text" />
              <ValidatedField
                id="pand-heeftVastgoedobject"
                name="heeftVastgoedobject"
                data-cy="heeftVastgoedobject"
                label="Heeft Vastgoedobject"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="pand-zonderverblijfsobjectligtinBuurt"
                name="zonderverblijfsobjectligtinBuurt"
                data-cy="zonderverblijfsobjectligtinBuurt"
                label="Zonderverblijfsobjectligtin Buurt"
                type="select"
              >
                <option value="" key="0" />
                {buurts
                  ? buurts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Maaktdeeluitvan Verblijfsobject"
                id="pand-maaktdeeluitvanVerblijfsobject"
                data-cy="maaktdeeluitvanVerblijfsobject"
                type="select"
                multiple
                name="maaktdeeluitvanVerblijfsobjects"
              >
                <option value="" key="0" />
                {verblijfsobjects
                  ? verblijfsobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pand" replace color="info">
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

export default PandUpdate;
