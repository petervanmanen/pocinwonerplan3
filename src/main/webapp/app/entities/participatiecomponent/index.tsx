import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Participatiecomponent from './participatiecomponent';
import ParticipatiecomponentDetail from './participatiecomponent-detail';
import ParticipatiecomponentUpdate from './participatiecomponent-update';
import ParticipatiecomponentDeleteDialog from './participatiecomponent-delete-dialog';

const ParticipatiecomponentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Participatiecomponent />} />
    <Route path="new" element={<ParticipatiecomponentUpdate />} />
    <Route path=":id">
      <Route index element={<ParticipatiecomponentDetail />} />
      <Route path="edit" element={<ParticipatiecomponentUpdate />} />
      <Route path="delete" element={<ParticipatiecomponentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParticipatiecomponentRoutes;
