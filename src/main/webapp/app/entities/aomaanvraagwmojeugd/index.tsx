import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aomaanvraagwmojeugd from './aomaanvraagwmojeugd';
import AomaanvraagwmojeugdDetail from './aomaanvraagwmojeugd-detail';
import AomaanvraagwmojeugdUpdate from './aomaanvraagwmojeugd-update';
import AomaanvraagwmojeugdDeleteDialog from './aomaanvraagwmojeugd-delete-dialog';

const AomaanvraagwmojeugdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aomaanvraagwmojeugd />} />
    <Route path="new" element={<AomaanvraagwmojeugdUpdate />} />
    <Route path=":id">
      <Route index element={<AomaanvraagwmojeugdDetail />} />
      <Route path="edit" element={<AomaanvraagwmojeugdUpdate />} />
      <Route path="delete" element={<AomaanvraagwmojeugdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AomaanvraagwmojeugdRoutes;
